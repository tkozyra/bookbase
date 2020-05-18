package com.bookbase.ui;

import com.bookbase.ui.views.authors.AuthorsView;
import com.bookbase.ui.views.books.BooksView;
import com.bookbase.ui.views.home.HomeView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

public class MainLayout extends AppLayout {
    public MainLayout(){
        createHeader();
        createDrawer();
    }

    private void createHeader(){
        H1 logo = new H1("Bookbase");
        logo.addClassName("logo");

        Anchor logout = new Anchor("/logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink homeLink = new RouterLink("Home", HomeView.class);
        RouterLink booksLink = new RouterLink("Books", BooksView.class);
        RouterLink authorsLink = new RouterLink("Authors", AuthorsView.class);
        homeLink.setHighlightCondition(HighlightConditions.sameLocation());
        booksLink.setHighlightCondition(HighlightConditions.sameLocation());
        authorsLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                homeLink,
                booksLink,
                authorsLink
        ));
    }
}
