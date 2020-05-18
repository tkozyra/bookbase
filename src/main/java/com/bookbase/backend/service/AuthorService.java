package com.bookbase.backend.service;

import com.bookbase.backend.entity.Author;
import com.bookbase.backend.repository.AuthorRepository;
import com.bookbase.backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AuthorService {

    private static final Logger LOGGER = Logger.getLogger(AuthorService.class.getName());

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    public long count(){
        return authorRepository.count();
    }

    public void delete(Author author){
        authorRepository.delete(author);
    }

    public void save(Author author){
        if (author != null) {
            authorRepository.save(author);
        }
        LOGGER.log(Level.SEVERE,
                "Author is null. Can't save null value in the database.");
    }

}
