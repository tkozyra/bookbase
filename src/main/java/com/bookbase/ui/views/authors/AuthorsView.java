package com.bookbase.ui.views.authors;

import com.bookbase.backend.entity.Author;
import com.bookbase.backend.entity.Book;
import com.bookbase.backend.service.AuthorService;
import com.bookbase.ui.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Authors", layout = MainLayout.class)
@PageTitle("Authors | Bookbase")
public class AuthorsView extends VerticalLayout {

//    private final AuthorForm authorForm;
    private Grid<Author> grid = new Grid<>(Author.class);
    private AuthorService authorService;

    public AuthorsView(AuthorService authorService){
        this.authorService = authorService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(
                new H1("Authors"),
                grid
        );
        updateList();
    }

    private void configureGrid() {
        grid.addClassName("author-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("books");
//        grid.removeColumnByKey("Best Book");
//        grid.removeColumnByKey("Rating");
        grid.addColumn(author -> {
            double rating = author.getRating();
            if(rating == 0)
                return "-";
            else
                return rating;
        }).setHeader("Average rating");

        grid.addColumn(author ->  {
            Book best = author.getBestBook();
            if(best == null)
                return "-";
            else
                return best.getTitle();
        }).setHeader("Best book");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList() {
        grid.setItems(authorService.findAll());
    }
}