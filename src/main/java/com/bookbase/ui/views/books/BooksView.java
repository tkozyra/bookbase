package com.bookbase.ui.views.books;

import com.bookbase.backend.entity.Author;
import com.bookbase.backend.entity.Book;
import com.bookbase.backend.entity.Category;
import com.bookbase.backend.service.BookService;
import com.bookbase.ui.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Books", layout = MainLayout.class)
@PageTitle("Books | Bookbase")
public class BooksView extends VerticalLayout {

    private Grid<Book> grid = new Grid<>(Book.class);
    private BookService bookService;

    public BooksView(BookService bookService){
        this.bookService = bookService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(
                new H1("Books"),
                grid
        );
        updateList();
    }

    private void updateList() {
        grid.setItems(bookService.findAll());
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
    }
}