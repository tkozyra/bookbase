package com.bookbase.backend.service;

import com.bookbase.backend.entity.Author;
import com.bookbase.backend.entity.Book;
import com.bookbase.backend.entity.Category;
import com.bookbase.backend.entity.Review;
import com.bookbase.backend.repository.AuthorRepository;
import com.bookbase.backend.repository.BookRepository;
import com.bookbase.backend.repository.CategoryRepository;
import com.bookbase.backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<Book> findAll(Category category) {
        List<Book> result = new ArrayList<>();
        findAll().forEach(book -> {
            if (book.getCategory().equals(category))
                result.add(book);
        });
        return result;
    }

    public List<Book> findAll(Author author) {
        List<Book> result = new ArrayList<>();
        findAll().forEach(book -> {
            if (book.getAuthor().equals(author))
                result.add(book);
        });
        return result;
    }

    public List<Book> findAll(String filter) {
        List<Book> books = this.findAll();
        List<Book> result = new ArrayList<>();
        for (Book book : books)
            if(book.getTitle().toLowerCase().contains(filter.toLowerCase()))
                result.add(book);
        return result;
    }

    public Book findBookByTitle(String title){
        return bookRepository.findBookByTitle(title);
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
        else
            LOGGER.log(Level.SEVERE,
                "Book is null. Can't save null value in the database.");
    }

    @PostConstruct
    public void populateTestData() {
        if (categoryRepository.count() == 0){
            categoryRepository.saveAll(
                    Stream.of("Science Fiction", "Horror", "Nonfiction")
                    .map(Category::new).collect(Collectors.toList()));
        }
        if (bookRepository.count() == 0) {
            Random r = new Random(0);
            List<Category> categories = categoryRepository.findAll();
            bookRepository.saveAll(
                    Stream.of("The institute", "It", "Recursion", "Los Pollos Hermanos", "Educated", "Bad blood")
                            .map(name -> {
                                        Book book = new Book();
                                        book.setTitle(name);
                                        book.setYear(r.nextInt()%20 + 2001);
//                                        book.setCategory(categories.get(r.nextInt()%2));
//                                        book.addReview(new Review(book, "user" + r.nextInt() % 3, "some review number " + r.nextInt() % 10, r.nextInt() % 5 + 1));
                                        return book;
                            })
                            .collect(Collectors.toList()));
        }
        if (authorRepository.count() == 0) {
            List<Book> books = bookRepository.findAll();
            List<Category> categories = categoryRepository.findAll();
            List<Author> authors = new ArrayList<>();

            Author author1 = new Author("Stephen", "King", 1947);
            books.get(0).setAuthor(author1);
            books.get(1).setAuthor(author1);
            books.get(0).setCategory(categoryRepository.findCategoryByName("Horror"));
            books.get(1).setCategory(categoryRepository.findCategoryByName("Horror"));
            books.get(0).setCoverImage("https://i.imgur.com/j6jkRgu.jpg");
            books.get(1).setCoverImage("https://i.imgur.com/ztTP2CG.jpg");
            authors.add(author1);

            Author author2 = new Author("Blake", "Crouch", 1954);

            books.get(2).setAuthor(author2);
            books.get(2).setCategory(categoryRepository.findCategoryByName("Science Fiction"));
            books.get(2).setCoverImage("https://i.imgur.com/mmpsHUM.jpg");
            authors.add(author2);

            Author author3 = new Author("Tara", "Westover", 1986);

            books.get(4).setAuthor(author3);
            books.get(4).setCategory(categoryRepository.findCategoryByName("Nonfiction"));
            books.get(4).setCoverImage("https://i.imgur.com/8dxTcI9.jpg");
            authors.add(author3);

            Author author4 = new Author("John", "Carreyrou", 1983);

            books.get(5).setAuthor(author4);
            books.get(5).setCategory(categoryRepository.findCategoryByName("Nonfiction"));
            books.get(5).setCoverImage("https://i.imgur.com/ouFpZM5.jpg");
            authors.add(author4);

            Author author5 = new Author("John", "Wick", 1954);
            authors.add(author5);
            books.get(3).setAuthor(author5);
            books.get(3).setCategory(categoryRepository.findCategoryByName("Science Fiction"));
            books.get(3).setCoverImage("https://i.imgur.com/64juBze.png");

            authorRepository.saveAll(authors);
            bookRepository.saveAll(books);
        }
//        if (reviewRepository.count() == 0) {
//            List<Book> books = bookRepository.findAll();
//            List<Review> reviews = new ArrayList<>();
//            for (Book book : books) {
//                int i = books.indexOf(book);
//                Review review = new Review(book, "user" + i, "some review number " + i, i % 5 + 1);
//                reviews.add(review);
//                review.addReviewToBook();
//            }
//            reviewRepository.saveAll(reviews);
//            bookRepository.saveAll(books);
//        }
    }
}
