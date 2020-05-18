package com.bookbase.ui.views.authors;


import com.bookbase.backend.entity.Author;
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

    private Grid<Author> grid = new Grid<>(Author.class);
    private AuthorService authorService;

    public AuthorsView(AuthorService authorService){
        this.authorService = authorService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(
                new H1("Authors")
        );
    }

    private void configureGrid() {
        grid.addClassName("author-grid");
        grid.setSizeFull();

    }
}