package com.bookbase.ui.views.authors;

import com.bookbase.backend.entity.Author;
import com.bookbase.backend.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AuthorDetails extends FormLayout {

    private Author author;
    private final BookService bookService;
    private final AuthorsView authorsView;

    private final Button buttonEdit = new Button("edit");
    private final Button buttonClose = new Button("close");

    public AuthorDetails(AuthorsView authorsView, BookService bookService) {
        addClassName("author-details");
        this.authorsView = authorsView;
        this.bookService = bookService;
    }

    protected void setDetails(Author author) {
        if (author != null) {
            this.author = author;
            H1 name = new H1(this.author.getFirstName() + " " + this.author.getSecondName());
            Paragraph birthYear = new Paragraph("Birth year: " + String.valueOf(this.author.getBirthYear()));
            Paragraph avgRating = new Paragraph("Average rating: " + String.valueOf(this.author.getRating()));
//            Paragraph bookList = new Paragraph();
//            bookService.findAll(this.author).forEach(book -> bookList.add(book.getTitle()));

            Icon iconEdit = new Icon(VaadinIcon.EDIT);
            buttonEdit.addClickListener(e -> authorsView.editAuthor(this.author));
            buttonEdit.addClassName("button-edit");
            buttonEdit.setIcon(iconEdit);
            Icon iconClose = new Icon(VaadinIcon.CLOSE);
            buttonClose.setIcon(iconClose);
            buttonClose.addClickListener(e -> authorsView.closeDetails());
            buttonClose.addClassName("button-close");


            Image authorImage = new Image("https://dummyimage.com/315x475/000/fff", "Author image");
            authorImage.addClassName("book-cover-image");

            if(author.getImage() != null){
                authorImage = new Image(author.getImage(), "Author image");
            }

            this.removeAll();
            this.add(new HorizontalLayout(authorImage, new VerticalLayout(name, birthYear, avgRating, new HorizontalLayout(buttonEdit, buttonClose))));
        }
        else
            this.author = null;
    }

    protected Author getCurrentAuthor() {
        return author;
    }
}
