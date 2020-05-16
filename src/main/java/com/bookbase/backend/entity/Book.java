package com.bookbase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookID;

    @NotNull
    @NotEmpty
    private String title;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Category category;

    private String description;

    public Book() {
    }

    public Book(@NotNull @NotEmpty String title, Author author, Category category, String description) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
