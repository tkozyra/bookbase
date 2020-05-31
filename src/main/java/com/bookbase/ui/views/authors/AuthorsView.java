package com.bookbase.ui.views.authors;

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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Route(value = "Authors", layout = MainLayout.class)
@PageTitle("Authors | Bookbase")
@CssImport("./styles/shared-styles.css")
public class AuthorsView extends VerticalLayout {

    private final TextField filterFirstName = new TextField();
    private final TextField filterSecondName = new TextField();
    private final AuthorForm authorForm;
    private final Grid<Author> grid = new Grid<>(Author.class);
    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;
    private final Select<Category> categorySelect = new Select<>();
    private final AuthorDetails authorDetails;

    public AuthorsView(AuthorService authorService, BookService bookService, CategoryService categoryService){
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        authorForm = new AuthorForm();
        authorForm.addListener(AuthorForm.SaveEvent.class, this::saveAuthor);
        authorForm.addListener(AuthorForm.CloseEvent.class, closeEvent -> closeForm());
        authorForm.addListener(AuthorForm.DeleteEvent.class, this::deleteAuthor);

        authorDetails = new AuthorDetails(this, this.bookService);
        authorDetails.addClassName("author-details");

        Div content = new Div(grid, authorDetails, authorForm);
        content.addClassName("content");
        content.setSizeFull();

        add(
                new H1("Authors"),
                getToolBar(),
                content
        );
        updateList();
        closeForm();
        closeDetails();
    }

    private HorizontalLayout getToolBar() {
        configureFilters();
        configureSelect();

        Button clearButton = new Button("X", buttonClickEvent -> updateList());
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button addAuthorButton = new Button("Add new author", buttonClickEvent -> addAuthor());
        addAuthorButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return new HorizontalLayout(filterFirstName, filterSecondName, categorySelect, clearButton, addAuthorButton);
    }

    private void configureSelect() {
        categorySelect.setPlaceholder("select category");
        categorySelect.addValueChangeListener(e -> updateList(categorySelect.getValue()));
        categorySelect.setItemLabelGenerator(Category::getName);
        categorySelect.setItems(categoryService.findAll());
        categorySelect.addDetachListener(e -> updateList());
    }

    private void configureFilters() {
        filterFirstName.setPlaceholder("Filter by first name");
        filterSecondName.setPlaceholder("Filter by second name");
        filterFirstName.setClearButtonVisible(true);
        filterSecondName.setClearButtonVisible(true);
        filterFirstName.setValueChangeMode(ValueChangeMode.LAZY);
        filterSecondName.setValueChangeMode(ValueChangeMode.LAZY);
        filterFirstName.addValueChangeListener(e -> updateListFirstName());
        filterSecondName.addValueChangeListener(e -> updateListSecondName());
    }

    private void addAuthor() {
        grid.asSingleSelect().clear();
        editAuthor(new Author());
    }

    private void deleteAuthor(AuthorForm.DeleteEvent deleteEvent) {
        authorService.delete(deleteEvent.getAuthor());
        updateList();
        closeForm();
    }

    private void saveAuthor(AuthorForm.SaveEvent saveEvent) {
        authorService.save(saveEvent.getAuthor());
        updateList();
        closeForm();
    }

    private void closeForm() {
        authorForm.setAuthor(null);
        authorForm.setVisible(false);
        authorDetails.setVisible(true);
        removeClassName("editing");
    }

    protected void editAuthor(Author author) {
        if(author == null)
            closeForm();
        else {
            authorDetails.setVisible(false);
            authorForm.setAuthor(author);
            authorForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void configureGrid() {
        grid.addClassName("author-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("fullName");
        grid.removeColumnByKey("rating");
        grid.removeColumnByKey("birthYear");

        grid.addColumn(author -> Calendar.getInstance().get(Calendar.YEAR) - author.getBirthYear())
                .setHeader("age");

        grid.addColumn(author -> {
            double rating = author.getRating();
            if(rating == 0) {
                return "-";
            }
            else {
                return rating;
            }
        }).setHeader("Average rating");


        grid.addColumn(author ->  {
            List<Book> books = bookService.findAll(author);
            if(books.size() == 0)
                return "-";
            else {
                Book best = books.get(0);
                for (Book book : books) {
                    if (book.getRating() > best.getRating())
                        best = book;
                }
                return best.getTitle();
            }
        }).setHeader("Best book");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> switchDetails(event.getValue()));
    }

    private void switchDetails(Author author) {
        if (author == null || (authorDetails.isVisible() && author.equals(authorDetails.getCurrentAuthor())))
            closeDetails();
        else {
            authorDetails.setDetails(author);
            closeForm();
            authorDetails.setVisible(true);
        }
    }

    protected void closeDetails() {
        authorDetails.setVisible(false);
        authorDetails.setDetails(null);
        grid.deselectAll();
    }

    private void updateList() {
        categorySelect.clear();
        grid.setItems(authorService.findAll());
    }

    private void updateList(Category value) {
        List<Author> authors = new ArrayList<>();
        bookService.findAll(value).forEach(book -> {
            if(!authors.contains(book.getAuthor()))
                authors.add(book.getAuthor());
        });
        grid.setItems(authors);
    }

    private void updateListFirstName() { grid.setItems(authorService.findAllByFirstName(filterFirstName.getValue()));}

    private void updateListSecondName() { grid.setItems(authorService.findAllBySecondName(filterSecondName.getValue()));}
}