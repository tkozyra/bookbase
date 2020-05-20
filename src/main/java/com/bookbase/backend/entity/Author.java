package com.bookbase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private int birthYear;

    @OneToMany
    private List<Book> books;

    private int ratingSum;

    private int ratingCount;

    public Author() {
    }

    public Author(@NotNull @NotEmpty String firstName,
                  @NotNull @NotEmpty String secondName, int birthYear) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthYear = birthYear;
        this.books = new ArrayList<>();
        ratingSum = 0;
        ratingCount = 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public double getRating() {
        if(ratingCount == 0)
            return 0;
        double rating = ((double) ratingSum) / ((double) ratingCount);
        return BigDecimal.valueOf(rating)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public void addRating(int rating) {
        ratingSum += rating;
        ratingCount++;
    }

//    public String getBestBookTitle() {
//        if(books.isEmpty())
//            return null;
//        else {
//            Book best = books.get(0);
//            for (Book book : books)
//                if(book.getRating() > best.getRating())
//                    best = book;
//            return best.getTitle();
//        }
//    }
}
