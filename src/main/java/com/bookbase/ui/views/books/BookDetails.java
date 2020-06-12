package com.bookbase.ui.views.books;

import com.bookbase.backend.entity.Book;
import com.bookbase.backend.entity.Review;
import com.bookbase.backend.service.BookService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class BookDetails extends VerticalLayout {

    private Book book;
    private BooksView booksView;

    private final Button buttonEdit = new Button("edit");
    private final Button buttonClose = new Button("close");
    private final Button buttonRate = new Button("rate");

    public BookDetails(BooksView booksView) {
        addClassName("book-details");
        this.booksView = booksView;
    }

    protected void setDetails(Book b) {
        this.book = b;
        if (book == null)
            return;

        H2 title = new H2(this.book.getTitle());

        Paragraph author = new Paragraph("Author: " + book.getAuthor().getFirstName() + " " +
                book.getAuthor().getSecondName());
        Paragraph categoryName = new Paragraph("Category: " + book.getCategory().getName());
        Paragraph description = new Paragraph(book.getDescription());


        Image cover = new Image("https://dummyimage.com/315x475/000/fff", "Book cover");
        cover.addClassName("book-cover-image");

        if(book.getCoverImage() != null){
            cover = new Image(book.getCoverImage(), "Book cover");
        }

        Icon iconEdit = new Icon(VaadinIcon.EDIT);
        buttonEdit.addClickListener(e -> booksView.editBook(this.book));
        buttonEdit.addClassName("button-edit");
        buttonEdit.setIcon(iconEdit);
        Icon iconClose = new Icon(VaadinIcon.CLOSE);
        buttonClose.addClickListener(e -> booksView.closeDetails());
        buttonClose.addClassName("button-close");
        buttonClose.setIcon(iconClose);
        Icon iconRate = new Icon(VaadinIcon.STAR_HALF_LEFT_O);
        buttonRate.addClickListener(e -> booksView.createReview(this.book));
        buttonRate.addClassName("button-rate");
        buttonRate.setIcon(iconRate);

        this.removeAll();

        HorizontalLayout buttonLayout = new HorizontalLayout(buttonRate, buttonEdit, buttonClose);
        HorizontalLayout mainLayout = new HorizontalLayout(cover, new VerticalLayout(title, author, categoryName, description, buttonLayout));

        VerticalLayout reviewsLayout = new VerticalLayout();
        List<Review> reviews = booksView.reviewService.findAll(this.book);
        if(reviews != null) {
            for (Review r : reviews) {
                reviewsLayout.add(getReviewComponent(r));
            }
        }
        else
            reviewsLayout.add(new Paragraph("No reviews yet"));

        this.add(new VerticalLayout(mainLayout, reviewsLayout));
    }

    private VerticalLayout getReviewComponent(Review review) {
        Icon arrowRight = VaadinIcon.ARROW_RIGHT.create();
        Paragraph nick = new Paragraph(review.getNickname());
        Paragraph rating = new Paragraph(review.getRating() + "/5");
        HorizontalLayout layout = new HorizontalLayout(nick, arrowRight, rating);
        layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Paragraph text;
        if (review.getContents() == null || review.getContents().equals(""))
            text = new Paragraph("No text review");
        else
            text = new Paragraph(review.getContents());
        return new VerticalLayout(new H3(layout), text);
    }


    protected Book getCurrentBook() {
        return this.book;
    }
}