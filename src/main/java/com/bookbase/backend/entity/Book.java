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

    private int year;

    private int ratingSum;

    private int ratingCount;

    public Book() {
    }

    public Book(@NotNull @NotEmpty String title, Author author, Category category, String description, int year) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.year = year;
        ratingSum = 0;
        ratingCount = 0;
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

    public int getYear() {
        return year;
    }

    public double getRating() {
        Double result = ((double) ratingSum) / ((double) ratingCount);
        result.setPrecision(1);
        return result.doubleValue();
    }

    public void addRating(int rating) {
        ratingSum += rating;
        ratingCount++;
    }
}
