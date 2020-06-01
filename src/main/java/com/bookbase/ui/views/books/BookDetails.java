package com.bookbase.ui.views.books;

import com.bookbase.backend.entity.Book;
import com.bookbase.backend.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class BookDetails extends VerticalLayout {

    private Book book;
    private BookService bookService;
    private BooksView booksView;

    private final Button buttonEdit = new Button("edit");
    private final Button buttonClose = new Button("close");
    private final Button buttonRate = new Button("rate");

    public BookDetails(BooksView booksView, BookService bookService) {
        addClassName("book-details");
        this.booksView = booksView;
        this.bookService = bookService;
    }

    protected void setDetails(Book book) {
        if (book != null) {
            this.book = book;
            H1 title = new H1(this.book.getTitle());
            Paragraph author = new Paragraph(book.getAuthor().getFirstName() + " " +
                    book.getAuthor().getSecondName());
            Paragraph categoryName = new Paragraph(book.getCategory().getName());
            Paragraph description = new Paragraph(book.getDescription());

            Image cover = new Image("https://dummyimage.com/600x400/000/fff", "Book cover");
            cover.addClassName("book-cover-image");

            if(book.getCoverImage() != null){
                cover = new Image(book.getCoverImage(), "Book cover");
            }

            buttonEdit.addClickListener(e -> booksView.editBook(this.book));
            buttonEdit.addClassName("button-edit");
            buttonClose.addClickListener(e -> booksView.closeDetails());
            buttonClose.addClassName("button-close");
            //clickListener todo
            buttonRate.addClassName("button-rate");

            this.removeAll();
            this.add(new HorizontalLayout(cover, new VerticalLayout(title, author, categoryName, description, new HorizontalLayout(buttonRate, buttonEdit, buttonClose))));
        }
        else
            this.book = null;
    }

    protected Book getCurrentBook() {
        return book;
    }
}