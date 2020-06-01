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

    @NotNull
    private int rating;

    private String contents;

    public Review(){
    }

    public Review(Book book, @NotEmpty @NotNull String nickname, String contents, @NotNull int rating) {
        this.book = book;
        this.nickname = nickname;
        this.contents = contents;
        this.rating = rating;
        addReviewToBook();
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

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", book=" + book +
                ", nickname='" + nickname + '\'' +
                ", rating=" + rating +
                ", contents='" + contents + '\'' +
                '}';
    }
}
