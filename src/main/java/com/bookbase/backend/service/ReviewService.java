package com.bookbase.backend.service;

import com.bookbase.backend.entity.Book;
import com.bookbase.backend.entity.Review;
import com.bookbase.backend.repository.BookRepository;
import com.bookbase.backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ReviewService {

    private static final Logger LOGGER = Logger.getLogger(ReviewService.class.getName());

    private ReviewRepository reviewRepository;
    private BookRepository bookRepository;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    public List<Review> findAll(){
        return reviewRepository.findAll();
    }

    public List<Review> findAll(Book book) {
        List<Review> reviews = new ArrayList<>();
        for (Review r : findAll()) {
            if (r.getBook().equals(book))
                reviews.add(r);
        }
        return reviews.isEmpty() ? null : reviews;
    }

    public long count(){
        return reviewRepository.count();
    }

    public void delete(Review review){
        reviewRepository.delete(review);
    }

    public void save(Review review){
        if (review != null) {
            reviewRepository.save(review);
        }
        else
            LOGGER.log(Level.SEVERE,
                "Review is null. Can't save null value in the database.");
    }
}
