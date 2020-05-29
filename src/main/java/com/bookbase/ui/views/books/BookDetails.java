package com.bookbase.ui.views.books;

import com.bookbase.backend.entity.Book;
import com.bookbase.backend.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class BookDetails extends VerticalLayout {

    private Book book;
    private String bookTitle;

    private BookService bookService;

    private H1 title;
    private Paragraph authorFirstname;
    private Paragraph categoryName;
    private Paragraph description;


    public void setBook(String bookTitle) {

        this.bookTitle = bookTitle;
        this.book = bookService.findBookByTitle(bookTitle);
        if(this.book != null){

            title = new H1(book.getTitle());
            authorFirstname = new Paragraph(book.getAuthor().getFirstName());
            categoryName = new Paragraph(book.getCategory().getName());
            description = new Paragraph(book.getDescription());

            add(
                    title, authorFirstname, categoryName, description
            );
        }
    }

    public BookDetails(BookService bookService) {
        addClassName("book-details");

        this.bookService = bookService;

        this.book = bookService.findBookByTitle(bookTitle);

        setBook(bookTitle);

    }
}