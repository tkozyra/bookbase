package com.bookbase.ui.views.books;

import com.bookbase.backend.entity.Author;
import com.bookbase.backend.entity.Book;
import com.bookbase.backend.entity.Category;
import com.bookbase.backend.service.AuthorService;
import com.bookbase.backend.service.BookService;
import com.bookbase.backend.service.CategoryService;
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

    private BookService bookService;
    private AuthorService authorService;
    private CategoryService categoryService;

    private final BookForm form;

    private Paragraph title = new Paragraph("");
    private Paragraph author = new Paragraph("");
    Div details;
//    Button buttonHideBookDetails = new Button("close");
//    VerticalLayout details = new VerticalLayout(title, author, buttonHideBookDetails);
    private final Select<Category> categorySelect = new Select<>();



    public BooksView(BookService bookService, AuthorService authorService, CategoryService categoryService){
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureFilter();

        form = new BookForm(authorService.findAll(), categoryService.findAll());
        form.addListener(BookForm.SaveEvent.class, this::saveBook);
        form.addListener(BookForm.DeleteEvent.class, this::deleteBook);
        form.addListener(BookForm.CloseEvent.class, event -> closeEditor());

        Div details = new Div(title, author);

        details.addClassName("book-details");
        Div content = new Div(grid, form, details);
        content.addClassName("content");
        content.setSizeFull();

        add(
                new H1("Books"),
                getToolBar(),
//                getBookDetails(),
                content
        );
        updateList();
        closeEditor();
        closeBookDetails();
    }

//    private VerticalLayout getBookDetails() {
//        Paragraph title = new Paragraph("");
//        Paragraph author = new Paragraph("");
//        Button buttonHideBookDetails = new Button("close");
//        buttonHideBookDetails.addClickListener(event -> closeBookDetails());
//        return new VerticalLayout(title, author, buttonHideBookDetails);
//    }

    private HorizontalLayout getToolBar() {
        configureFilter();
        configureSelect();

        Button clearButton = new Button("X", buttonClickEvent -> updateList());
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button bookButton = new Button("Add new book", buttonClickEvent -> addBook());
        bookButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return new HorizontalLayout(filterText, categorySelect, clearButton, bookButton);
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
    }

    private void deleteBook(BookForm.DeleteEvent event){
        bookService.delete(event.getBook());
        updateList();
        closeEditor();
    }

    private void closeEditor(){
        form.setBook(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void editBook(Book book){
        if (book == null){
            closeEditor();
        } else{
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

        grid.asSingleSelect().addValueChangeListener(event -> editBook(event.getValue()));
        grid.asSingleSelect().addValueChangeListener(event -> passBookDetails(event.getValue()));

    }

    private void passBookDetails(Book book) {
        if (book == null){
            closeBookDetails();
        } else {
            title.setText(book.getTitle());
        }
    }

    private void closeBookDetails(){

    }
}