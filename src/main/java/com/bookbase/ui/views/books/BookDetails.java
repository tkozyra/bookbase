package com.bookbase.ui.views.books;

import com.bookbase.backend.entity.Book;
import com.bookbase.backend.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class BookDetails extends VerticalLayout {

    private Book book;
    private BookService bookService;
    private BooksView booksView;

    private final Button editButton = new Button("edit");
    private final Button closeButton = new Button("close");

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

            editButton.addClickListener(e -> booksView.editBook(this.book));
            closeButton.addClickListener(e -> booksView.closeDetails());

            this.removeAll();
            this.add(new HorizontalLayout(title, editButton, closeButton), author, categoryName, description);
        }
        else
            this.book = null;
    }

    protected Book getCurrentBook() {
        return book;
    }
}