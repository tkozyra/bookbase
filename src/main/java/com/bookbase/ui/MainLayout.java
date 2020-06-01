package com.bookbase.ui;

import com.bookbase.ui.views.authors.AuthorsView;
import com.bookbase.ui.views.books.BooksView;
import com.bookbase.ui.views.home.HomeView;
import com.bookbase.ui.views.login.LoginView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.*;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {
    public MainLayout(){
        createNavbar();
    }

    private void createNavbar(){
        Image logo = new Image("https://i.imgur.com/CeLGynm.png", "Bookbase logo");
        logo.addClassName("logo");

        FlexLayout centeredLayout = new FlexLayout();
        centeredLayout.setSizeFull();
        centeredLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        centeredLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        centeredLayout.addClassName("navbar");

        Icon homeIcon = new Icon(VaadinIcon.HOME);
        Tab home = new Tab(new RouterLink("Home", HomeView.class));
        home.addClassName("navbar-link");
        home.addComponentAsFirst(homeIcon);

        Icon bookIcon = new Icon(VaadinIcon.OPEN_BOOK);
        Tab books = new Tab(new RouterLink("Books", BooksView.class));
        books.addClassName("navbar-link");
        books.addComponentAsFirst(bookIcon);

        Icon authorIcon = new Icon(VaadinIcon.USERS);
        Tab authors = new Tab(new RouterLink("Authors", AuthorsView.class));
        authors.addClassName("navbar-link");
        authors.addComponentAsFirst(authorIcon);

//        Icon signOutIcon = new Icon(VaadinIcon.SIGN_OUT_ALT);
//        Tab signOut = new Tab(new RouterLink("Sign Out", LoginView.class));
//        signOut.addClassName("navbar-link");
//        signOut.addComponentAsFirst(signOutIcon);

        Tabs tabs = new Tabs(home, books, authors);
        centeredLayout.add(tabs);
        addToNavbar(false, logo, centeredLayout);
    }
}
