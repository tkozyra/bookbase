package com.bookbase.ui.views.authors;

import com.bookbase.backend.entity.Author;
import com.bookbase.backend.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AuthorDetails extends FormLayout {

    private Author author;
    private BookService bookService;
    private AuthorsView authorsView;

    private H1 name;
    private Paragraph birthYear;
    private Paragraph avgRating;
    private Paragraph bookList;

    private Button editButton = new Button("edit");

    public AuthorDetails(AuthorsView authorsView, BookService bookService) {
        addClassName("author-details");
        this.authorsView = authorsView;
        this.bookService = bookService;

        setDetails(this.author);
    }

    protected void setDetails(Author author) {
        if (author != null) {
            this.author = author;
            name = new H1(this.author.getFirstName() + " " + this.author.getSecondName());
            birthYear = new Paragraph(String.valueOf(author.getBirthYear()));
            avgRating = new Paragraph(String.valueOf(author.getRating()));
            bookList = new Paragraph();
            bookService.findAll(this.author).forEach(book -> bookList.add(book.getTitle()));

            editButton.addClickListener(e -> authorsView.editAuthor(this.author));

            this.removeAll();
            this.add(new HorizontalLayout(name, editButton), birthYear, avgRating, bookList);
        }
    }
}
