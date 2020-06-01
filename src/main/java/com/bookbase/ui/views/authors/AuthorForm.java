package com.bookbase.ui.views.authors;

import com.bookbase.backend.entity.Author;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

@CssImport("./styles/shared-styles.css")
public class AuthorForm extends FormLayout {

    private TextField firstName = new TextField("first name");
    private TextField secondName = new TextField("second name");
    private IntegerField birthYear = new IntegerField("birth year");

    private Button save = new Button("save");
    private Button delete = new Button("delete");
    private Button close = new Button("close");

    private Binder<Author> binder = new BeanValidationBinder<>(Author.class);

    public AuthorForm() {
        addClassName("author-form");
        binder.bindInstanceFields(this);

        add(
                firstName,
                secondName,
                birthYear,
                createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setAuthor(Author author) {
        binder.setBean(author);
    }

    private void validateAndSave() {
        if(binder.isValid())
            fireEvent(new SaveEvent(this, binder.getBean()));
    }

    public static abstract class AuthorFormEvent extends ComponentEvent<AuthorForm> {
        private Author author;
        protected AuthorFormEvent(AuthorForm source, Author author) {
            super(source, false);
            this.author = author;
        }

        public Author getAuthor() {
            return author;
        }
    }

    public static class DeleteEvent extends AuthorFormEvent{
        DeleteEvent(AuthorForm source, Author author) {
            super(source, author);
        }
    }

    public static class CloseEvent extends AuthorFormEvent{
        CloseEvent(AuthorForm source) {
            super(source, null);
        }
    }

    public static class SaveEvent extends AuthorFormEvent{
        SaveEvent(AuthorForm source, Author author) {
            super(source, author);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
