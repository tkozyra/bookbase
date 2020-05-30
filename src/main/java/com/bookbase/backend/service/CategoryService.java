package com.bookbase.backend.service;

import com.bookbase.backend.entity.Author;
import com.bookbase.backend.entity.Category;
import com.bookbase.backend.repository.BookRepository;
import com.bookbase.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CategoryService {
    private static final Logger LOGGER = Logger.getLogger(CategoryService.class.getName());

    private CategoryRepository categoryRepository;
    private BookRepository bookRepository;

    public CategoryService(CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public long count(){
        return categoryRepository.count();
    }

    public void delete(Category category){
        categoryRepository.delete(category);
    }

    public void save(Category category){
        if (category != null) {
            categoryRepository.save(category);
        }
        else
            LOGGER.log(Level.SEVERE,
                "Category is null. Can't save null value in the database.");
    }
}
