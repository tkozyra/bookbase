package com.bookbase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reviewID;

    @ManyToOne
    private Book book;

    @NotEmpty
    @NotNull
    private String nickname;

    @NotEmpty
    @NotNull
    private int rating;

    private String contents;

    public Review(){
    }

    public Review(Book book, @NotEmpty @NotNull String nickname, String contents, @NotEmpty @NotNull int rating) {
        this.book = book;
        this.nickname = nickname;
        this.contents = contents;
        this.rating = rating;
    }
    public void addReviewToBook(){
        this.book.addReview(this);
    }

    public Book getBook() {
        return book;
    }

    public String getNickname() {
        return nickname;
    }

    public int getRating() {
        return rating;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
