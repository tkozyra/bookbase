package com.bookbase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int categoryID;

    @NotEmpty
    @NotNull
    private String name;

//    @OneToMany
//    private List<Book> books;

    public Category() {
    }

    public Category(@NotEmpty @NotNull String name) {
       // this.books = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

//    public List<Book> getBooks() {
//        return books;
//    }

//    public void addBook(Book book) {
//        this.books.add(book);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryID == category.categoryID &&
                name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryID, name);
    }
}
