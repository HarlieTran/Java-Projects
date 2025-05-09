package com.example.application.views.list;

import com.example.application.data.Company;
import com.example.application.data.Product;
import com.example.application.data.Status;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ProductForm extends FormLayout {
    TextField name = new TextField("Name");
    TextField description = new TextField("Description");
    NumberField price = new NumberField("Price");
    ComboBox<Status> status = new ComboBox<>("Status");
    ComboBox<Company> company = new ComboBox<>("Company");
    TextField imageUrl = new TextField("Image URL");


    Button save = new Button("Save");
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete");

    private Product product;
    Binder<Product> binder = new BeanValidationBinder<>(Product.class);

    public ProductForm(List<Status> statuses, List<Company> companies) {
        addClassName("ProductForm");
        binder.bindInstanceFields(this);

        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        status.setItems(statuses);
        status.setItemLabelGenerator(Status::getName);

        add(name, description, price, status, company, imageUrl, createButtons());
    }

    public void setProduct(Product product) {
        this.product = product;
        binder.readBean(product);
    }

    private HorizontalLayout createButtons() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, product)));
        cancel.addClickListener(event -> fireEvent(new CancelEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, cancel, delete);
    }

    private void validateSave() {
        try {
            binder.writeBean(product);
            fireEvent(new SaveEvent(this, product));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    protected static abstract class ProductFormEvent extends ComponentEvent<ProductForm> {
        private Product product;

        public ProductFormEvent(ProductForm productForm, Product product) {
            super(productForm, false);
            this.product = product;
        }
        public Product getProduct() {
            return product;
        }
    }

    public static class SaveEvent extends ProductFormEvent {
        SaveEvent(ProductForm productForm, Product product) {
            super(productForm, product);
        }
    }

    public static class CancelEvent extends ProductFormEvent {
        CancelEvent(ProductForm productForm) {
            super(productForm, null);
        }
    }

    public static class DeleteEvent extends ProductFormEvent {
        DeleteEvent(ProductForm productForm, Product product) {
            super(productForm, product);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
