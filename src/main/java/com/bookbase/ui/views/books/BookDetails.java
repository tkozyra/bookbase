package com.bookbase.ui.views.books;

import com.bookbase.backend.entity.Book;
import com.bookbase.backend.entity.Review;
import com.bookbase.backend.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

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

    protected void setDetails(Book book) {
        if (book != null) {
            this.book = book;
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

            HorizontalLayout mainLayout = new HorizontalLayout(cover, new VerticalLayout(title, author, categoryName, description, new HorizontalLayout(buttonRate, buttonEdit, buttonClose)));
            VerticalLayout reviewsLayout = new VerticalLayout();
            reviewsLayout.add();
            for (Review review : booksView.reviewService.findAll()) {
                if (review.getBook().equals(book)) {
//                    reviews.add(new VerticalLayout(new HorizontalLayout(review.getNickname(), String.valueOf(review.getRating())), review.getContents()));
                    HorizontalLayout nickAndRate = new HorizontalLayout(new Paragraph(review.getNickname()), new Paragraph(String.valueOf(review.getRating())));
                    reviewsLayout.add(new VerticalLayout(nickAndRate, new Paragraph(review.getContents())));
                }
            }
            this.add(new VerticalLayout(mainLayout, reviewsLayout));
        }
        else
            this.book = null;
    }


    protected Book getCurrentBook() {
        return book;
    }
}