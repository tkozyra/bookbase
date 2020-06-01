package com.bookbase.ui.views.books;

import com.bookbase.backend.entity.Book;
import com.bookbase.backend.entity.Review;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class BookReview extends FormLayout {

    private final BooksView booksView;
    private Book book;

    private H1 title = new H1();
    private TextField nickField = new TextField("Nick");
    private Select<Integer> selectRate = new Select<>();
    private TextArea contentField = new TextArea("Contents");

    private Button addButton = new Button("add");
    private Button cancelButton = new Button("cancel");

//    private Binder<Review> binder = new BeanValidationBinder<>(Review.class);

    public BookReview(BooksView booksView) {
        this.booksView = booksView;
//        binder.bindInstanceFields(this);

        selectRate.setItems(1,2,3,4,5);
        selectRate.setLabel("Rating");
        nickField.addValueChangeListener(e -> checkFields());
        selectRate.addValueChangeListener(e -> checkFields());

        add(new VerticalLayout(
                title,
                nickField,
                selectRate,
                contentField,
                configureButtons()
        ));
    }

    private void checkFields() {
        if (nickField.getValue() != null && !nickField.getValue().equals("") &&
            selectRate.getValue() != null)
            addButton.setEnabled(true);
        else
            addButton.setEnabled(false);
    }

    private HorizontalLayout configureButtons() {
        addButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addButton.addClickListener(click -> createReview());
        cancelButton.addClickListener(click -> fireEvent(new CloseEvent(this)));

        addButton.addClickShortcut(Key.ENTER);
        cancelButton.addClickShortcut(Key.ESCAPE);

//        binder.addStatusChangeListener(event -> addButton.setEnabled(binder.isValid()));
        addButton.setEnabled(false);

        return new HorizontalLayout(addButton, cancelButton);
    }

    protected void setBook(Book book) {
        this.book = book;
    }

//    protected void createReview(Book book) {
    protected void createReview() {
        if(this.book != null) {
//            this.book = book;
            title = new H1(book.getTitle());
            String nick = nickField.getValue();
            int rating = selectRate.getValue();
            String content = contentField.getValue();

            Review review = new Review(book, nick, content, rating);
            nickField.clear();
            selectRate.clear();
            contentField.clear();
            booksView.addReview(review);
        }
    }

    public Book getBook() {
        return book;
    }

//    private void validateAndSave() {
//        if (binder.isValid()) {
//            fireEvent(new SaveEvent(this, binder.getBean()));
//        }
//    }

    // Events
    public static abstract class BookReviewEvent extends ComponentEvent<BookReview> {
        private Review review;

        protected BookReviewEvent(BookReview source, Review review) {
            super(source, false);
            this.review = review;
        }

        public Review getReview() {
            return review;
        }
    }

    public static class SaveEvent extends BookReviewEvent {
        SaveEvent(BookReview source, Review review) {
            super(source, review);
        }
    }

    public static class CloseEvent extends BookReviewEvent {
        CloseEvent(BookReview source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}