package com.bookbase.ui.views.authors;

import com.bookbase.backend.entity.Author;
import com.bookbase.backend.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class AuthorDetails extends FormLayout {

    private Author author;
    private final BookService bookService;
    private final AuthorsView authorsView;

    private final Button editButton = new Button("edit");
    private final Button closeButton = new Button("close");

    public AuthorDetails(AuthorsView authorsView, BookService bookService) {
        addClassName("author-details");
        this.authorsView = authorsView;
        this.bookService = bookService;
    }

    protected void setDetails(Author author) {
        if (author != null) {
            this.author = author;
            H1 name = new H1(this.author.getFirstName() + " " + this.author.getSecondName());
            Paragraph birthYear = new Paragraph(String.valueOf(this.author.getBirthYear()));
            Paragraph avgRating = new Paragraph(String.valueOf(this.author.getRating()));
            Paragraph bookList = new Paragraph();
            bookService.findAll(this.author).forEach(book -> bookList.add(book.getTitle()));

            editButton.addClickListener(e -> authorsView.editAuthor(this.author));
            closeButton.addClickListener(e -> authorsView.closeDetails());

            this.removeAll();
            this.add(new HorizontalLayout(name, editButton, closeButton), birthYear, avgRating, bookList);
        }
        else
            this.author = null;
    }

    protected Author getCurrentAuthor() {
        return author;
    }
}
