package com.bookbase.ui.views.books;

import com.bookbase.backend.entity.Author;
import com.bookbase.backend.entity.Book;
import com.bookbase.backend.entity.Category;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import javax.validation.constraints.NotNull;
import java.util.List;

public class BookForm extends FormLayout {


    private TextField title = new TextField("Title");
    private ComboBox<Author> author = new ComboBox<>("Author");
    protected ComboBox<Category> category = new ComboBox<>("Category");
    private TextArea description = new TextArea("Description");
    private IntegerField year = new IntegerField("Year");

    private final Button save = new Button("save");
    private final Button delete = new Button("delete");
    private final Button close = new Button("close");

    private Binder<Book> binder = new BeanValidationBinder<>(Book.class);

    public BookForm(List<Author> authors, List<Category> categories){
        addClassName("book-form");

        binder.bindInstanceFields(this);

        author.setItems(authors);
        author.setItemLabelGenerator(Author::getFullName);
        author.setRequired(true);

        category.setItems(categories);
        category.setItemLabelGenerator(Category::getName);

        category.setRequired(true);

        add(
                title,
                author,
                description,
                category,
                year,
                createButtonsLayout()
        );
    }


    public void setBook(Book book){
        binder.setBean(book);
    }

    private Component createButtonsLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid() && author.getValue()!=null
        && category.getValue() != null));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave(){
        if(binder.isValid()){
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    // Events
    public static abstract class BookFormEvent extends ComponentEvent<BookForm> {
        private Book book;

        protected BookFormEvent(BookForm source, Book book) {
            super(source, false);
            this.book = book;
        }

        public Book getBook() {
            return book;
        }
    }

    public static class SaveEvent extends BookFormEvent {
        SaveEvent(BookForm source, Book book) {
            super(source, book);
        }
    }

    public static class DeleteEvent extends BookFormEvent {
        DeleteEvent(BookForm source, Book book) {
            super(source, book);
        }
    }

    public static class CloseEvent extends BookFormEvent {
        CloseEvent(BookForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}