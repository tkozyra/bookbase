package com.bookbase.ui.views;


import com.bookbase.ui.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Authors", layout = MainLayout.class)
@PageTitle("Authors | Bookbase")
public class AuthorsView extends VerticalLayout {

    public AuthorsView(){
        add(
                new H1("Authors")
        );
    }
}