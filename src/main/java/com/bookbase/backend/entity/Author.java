package com.bookbase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int authorID;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String secondName;

    @OneToMany
    private List<Book> books;

    public Author() {
    }

    public Author(@NotNull @NotEmpty String firstName, @NotNull @NotEmpty String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.books = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }
}
