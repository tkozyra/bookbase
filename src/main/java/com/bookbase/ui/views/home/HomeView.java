package com.bookbase.ui.views.home;

import com.bookbase.ui.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Bookbase")
public class HomeView extends VerticalLayout {
    public HomeView(){
        addClassName("home-view");
//        Image img = new Image("https://i.imgur.com/npPmFJQ.jpg", "Cover");
//        img.addClassName("home-page-cover");
//        add(
//                img
//        );
    }
}