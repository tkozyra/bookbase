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

    private String image;

    private int ratingSum;

    private int ratingCount;

    public Author() {
    }

    public Author(@NotNull @NotEmpty String firstName,
                  @NotNull @NotEmpty String secondName, int birthYear) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthYear = birthYear;
        ratingSum = 0;
        ratingCount = 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFullName(){
        return firstName + " " + secondName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return authorID == author.authorID &&
                birthYear == author.birthYear &&
                ratingSum == author.ratingSum &&
                ratingCount == author.ratingCount &&
                firstName.equals(author.firstName) &&
                secondName.equals(author.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorID, firstName, secondName, birthYear, ratingSum, ratingCount);
    }
}
