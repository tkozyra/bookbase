package com.bookbase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

//    @OneToMany
//    private List<Review> reviewList;

    private String coverImage;

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
//        this.coverImage = coverImage;
        ratingSum = 0;
        ratingCount = 0;
//        reviewList = new ArrayList<>();
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

    public String getCoverImage() {
        return coverImage;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        if(ratingCount == 0)
            return 0;
        double result = ((double) ratingSum) / ((double) ratingCount);
        return BigDecimal.valueOf(result)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

//    public List<Review> getReviewList() {
//        return reviewList;
//    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private void addRating(int rating) {
        ratingSum += rating;
        ratingCount++;
    }

    public void addReview(Review review) {
//        this.reviewList.add(review);
        this.author.addRating(review.getRating());
        this.addRating(review.getRating());
    }

    public void deleteReview(Review review) {
        this.author.removeRating(review.getRating());
        this.removeRating(review.getRating());
    }

    private void removeRating(int rating) {
        ratingSum -= rating;
        ratingCount--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookID == book.bookID &&
                year == book.year &&
                ratingSum == book.ratingSum &&
                ratingCount == book.ratingCount &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(category, book.category) &&
                Objects.equals(coverImage, book.coverImage) &&
                Objects.equals(description, book.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookID, title, author, category, coverImage, description, year, ratingSum, ratingCount);
    }
}
