package com.bookbase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

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

    @OneToOne
    private Review review;

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
        review = null;
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
        double result = ((double) ratingSum) / ((double) ratingCount);
        return BigDecimal.valueOf(result)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public void addRating(int rating) {
        ratingSum += rating;
        ratingCount++;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

}
