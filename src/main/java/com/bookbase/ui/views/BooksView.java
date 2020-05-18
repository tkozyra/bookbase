package com.bookbase.ui.views;

import com.bookbase.ui.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Books", layout = MainLayout.class)
@PageTitle("Books | Bookbase")
public class BooksView extends VerticalLayout {
    public BooksView(){
        add(
                new H1("Books")
        );
    }
}