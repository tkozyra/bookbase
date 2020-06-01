package com.bookbase.ui.views.books;

import com.bookbase.backend.entity.Category;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class CategoryForm extends FormLayout {


    private final TextField categoryName = new TextField("Category name");
//    private ComboBox<Author> author = new ComboBox<>("Author");
//    private ComboBox<Category> category = new ComboBox<>("Category");
//    private TextArea description = new TextArea("Description");
//    private IntegerField year = new IntegerField("Year");

    private final Button save = new Button("save");
    private final Button delete = new Button("delete");
    private final Button close = new Button("close");

//    private Binder<Category> binder = new BeanValidationBinder<>(Category.class);

    public CategoryForm(){
        addClassName("category-form");
        categoryName.addValueChangeListener(event -> check());
//        binder.bindInstanceFields(this);
        save.setEnabled(false);

        add(
                categoryName,
                createButtonsLayout()
        );
    }

    private void check() {
        if(categoryName.getValue()!=null && !categoryName.getValue().equals("")){
            save.setEnabled(true);
        }
        else{
            save.setEnabled(false);
        }
    }


//    public void setCategory(Category category){
//        binder.setBean(category);
//    }

    private Component createButtonsLayout(){

        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
//        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

//        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save,  close);
    }

    private void validateAndSave(){
////        if(binder.isValid()){
            fireEvent(new SaveEvent(this, new Category(categoryName.getValue())));
////        }
    }

    // Events
    public static abstract class CategoryFormEvent extends ComponentEvent<CategoryForm> {
        private Category category;

        protected CategoryFormEvent(CategoryForm source, Category category) {
            super(source, false);
            this.category = category;
        }

        public Category getCategory() {
            return category;
        }
    }

    public static class SaveEvent extends CategoryFormEvent {
        SaveEvent(CategoryForm source, Category category) {
            super(source, category);
        }
    }

    public static class DeleteEvent extends CategoryFormEvent {
        DeleteEvent(CategoryForm source, Category category) {
            super(source, category);
        }
    }

    public static class CloseEvent extends CategoryFormEvent {
        CloseEvent(CategoryForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}