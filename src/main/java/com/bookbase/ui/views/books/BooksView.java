package com.bookbase.ui.views.books;

import com.bookbase.backend.entity.Author;
import com.bookbase.backend.entity.Book;
import com.bookbase.backend.entity.Category;
import com.bookbase.backend.entity.Review;
import com.bookbase.backend.service.AuthorService;
import com.bookbase.backend.service.BookService;
import com.bookbase.backend.service.CategoryService;
import com.bookbase.backend.service.ReviewService;
import com.bookbase.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "Books", layout = MainLayout.class)
@PageTitle("Books | Bookbase")
@CssImport("./styles/shared-styles.css")
public class BooksView extends VerticalLayout {

    private Grid<Book> grid = new Grid<>(Book.class);
    private TextField filterText = new TextField();

    private final BookService bookService;
    private final AuthorService authorService;
    protected final CategoryService categoryService;
    protected final ReviewService reviewService;

    private final BookForm form;
    private final CategoryForm categoryForm;

    private Paragraph title = new Paragraph("");
    private Paragraph author = new Paragraph("");
    private final Select<Category> categorySelect = new Select<>();
    private final BookDetails bookDetails;
    private final BookReview bookReview;



    public BooksView(BookService bookService, AuthorService authorService,
                     CategoryService categoryService, ReviewService reviewService){
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.reviewService = reviewService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureFilter();

        form = new BookForm(authorService.findAll(), categoryService.findAll());
        form.addListener(BookForm.SaveEvent.class, this::saveBook);
        form.addListener(BookForm.DeleteEvent.class, this::deleteBook);
        form.addListener(BookForm.CloseEvent.class, event -> closeEditor());

        categoryForm = new CategoryForm();
        categoryForm.setVisible(false);
        categoryForm.addListener(CategoryForm.SaveEvent.class, this::saveCategory);
        categoryForm.addListener(CategoryForm.CloseEvent.class, event -> closeCategory());
        categoryForm.addClassName("add-category");

        bookReview = new BookReview(this);
        bookReview.setVisible(false);
        bookReview.addClassName("add-review");

        bookReview.addListener(BookReview.SaveEvent.class, this::saveReview);
        bookReview.addListener(BookReview.CloseEvent.class, event -> closeReview());

        bookDetails = new BookDetails(this);
        bookDetails.addClassName("book-details");

        Div content = new Div(grid, bookDetails, form, categoryForm, bookReview);
        content.addClassName("content");
        content.setSizeFull();

        add(
                new H1("Books"),
                getToolBar(),
                content
        );
        updateList();
        closeEditor();
        closeDetails();
    }


    private HorizontalLayout getToolBar() {
        configureFilter();
        configureSelect();

        Button clearButton = new Button("X", buttonClickEvent -> updateList());
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button bookButton = new Button("Add new book", buttonClickEvent -> addBook());
        bookButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button categoryButton = new Button("Add category", categoryClickEvent -> addCategory());
        categoryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return new HorizontalLayout(filterText, categorySelect, clearButton, bookButton,categoryButton); //
    }

    private void configureSelect() {
        categorySelect.setPlaceholder("select category");
        categorySelect.addValueChangeListener(e -> updateList(categorySelect.getValue()));
        categorySelect.setItemLabelGenerator(Category::getName);
        categorySelect.setItems(categoryService.findAll());
        categorySelect.addDetachListener(e -> updateList());
    }

    private void addBook() {
        grid.asSingleSelect().clear();
        editBook(new Book());
    }

    private void saveBook(BookForm.SaveEvent event){
        bookService.save(event.getBook());
        updateList();
        closeEditor();
        closeDetails();
    }

    private void saveReview(BookReview.SaveEvent event) {
        reviewService.save(event.getReview());
        updateList();
        closeReview();
    }

    private void deleteBook(BookForm.DeleteEvent event){
        deleteBooksReviews(event.getBook());
        bookService.delete(event.getBook());
        updateList();
        closeEditor();
        closeDetails();
    }

    private void deleteBooksReviews(Book book) {
      List<Review> reviews = reviewService.findAll();
        for (Review r : reviews) {
            if (r.getBook().equals(book))
                r.getBook().deleteReview(r);
                reviewService.delete(r);
        }
    }

    protected void editBook(Book book){
        if (book == null){
            closeEditor();
        } else{
            bookDetails.setVisible(false);
            form.setBook(book);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateList() {
        categorySelect.clear();
        grid.setItems(bookService.findAll());
    }

    private void updateList(Category value) {
        List<Book> books = new ArrayList<>();
        bookService.findAll().forEach(book -> {
            if (book.getCategory().equals(value))
                books.add(book);
        });
        grid.setItems(books);
    }

    private void updateListByName() {
        grid.setItems(bookService.findAll(filterText.getValue()));
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by title");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateListByName());
    }

    private void configureGrid() {
        grid.addClassName("books-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("author");
        grid.removeColumnByKey("category");
        grid.setColumns("title", "year", "rating");
        grid.addColumn(book -> {
            Author author = book.getAuthor();
            if(author == null){
                return "-";
            }else {
                return author.getFirstName() + " " + author.getSecondName();
            }
        }).setHeader("Author");

        grid.addColumn(book -> {
            Category category = book.getCategory();
            if(category == null){
                return "-";
            } else {
                return category.getName();
            }
        }).setHeader("Category");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> switchDetails(event.getValue()));
    }

    protected void createReview(Book book) {
        if (book == null)
            closeReview();
        else {
            bookDetails.setVisible(false);
            bookReview.setVisible(true);
            bookReview.setBook(book);
        }
    }

    protected void addReview(Review review) {
        bookService.save(review.getBook());
        authorService.save(review.getBook().getAuthor());
        reviewService.save(review);
        grid.setItems(bookService.findAll());
        bookDetails.setDetails(bookDetails.getCurrentBook());
        bookDetails.setVisible(true);
        closeReview();
    }

    private void addCategory(){
        grid.asSingleSelect().clear();
        bookDetails.setVisible(false);
        bookReview.setVisible(false);
        categoryForm.setVisible(true);
    }

    private void saveCategory(CategoryForm.SaveEvent event){
        categoryService.save(event.getCategory());
        updateList();
        closeCategory();
        categorySelect.setItems(categoryService.findAll());
        form.category.setItems(categoryService.findAll());
    }

    private void closeCategory(){
        categoryForm.setVisible(false);
        bookReview.setVisible(false);
        bookDetails.setVisible(false);
    }

    private void closeReview(){
        bookReview.setVisible(false);
        bookDetails.setVisible(true);
        bookReview.setBook(null);
    }

    protected void closeDetails() {
        bookDetails.setVisible(false);
//        bookDetails.setDetails(null);
//        grid.deselectAll();
    }

    private void closeEditor(){
        form.setBook(null);
        form.setVisible(false);
        bookDetails.setVisible(true);
        removeClassName("editing");
    }

    private void switchDetails(Book book) {
        if (book == null) {
            closeDetails();
            closeReview();
        }
        else if (bookDetails.isVisible() && book.equals(bookDetails.getCurrentBook()))
            closeDetails();
        else if (bookReview.isVisible() && book.equals(bookReview.getBook()))
            closeReview();
        else {
            closeReview();
            bookDetails.setDetails(book);
            closeEditor();
            bookDetails.setVisible(true);
        }
    }
}