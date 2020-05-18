package com.bookbase.backend.service;

import com.bookbase.backend.entity.Author;
import com.bookbase.backend.entity.Book;
import com.bookbase.backend.entity.Category;
import com.bookbase.backend.repository.AuthorRepository;
import com.bookbase.backend.repository.BookRepository;
import com.bookbase.backend.repository.CategoryRepository;
import com.bookbase.backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BookService {
    private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());

    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;
    private ReviewRepository reviewRepository;
    private AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository,
                       ReviewRepository reviewRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;

    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public long count(){
        return bookRepository.count();
    }

    public void delete(Book book){
        bookRepository.delete(book);
    }

    public void save(Book book){
        if (book != null) {
            bookRepository.save(book);
        }
        LOGGER.log(Level.SEVERE,
                "Author is null. Can't save null value in the database.");
    }
}
