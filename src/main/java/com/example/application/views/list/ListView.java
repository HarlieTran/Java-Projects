package com.example.application.views.list;


import com.example.application.data.Product;
import com.example.application.layout.AdminView;
import com.example.application.services.ProductService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed("ADMIN")
@PageTitle("Products | E-commerce")
@Route(value = "product-management", layout = AdminView.class)
public class ListView extends VerticalLayout {

    Grid<Product> grid = new Grid<>(Product.class);
    TextField filter = new TextField();
    ProductForm productForm;

    ProductService productService;

    public ListView(ProductService productService) {
        this.productService = productService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureProductForm();
        add(getToolbar(),getContent());
        updateList();
        closeEditor();
    }

    private void editProduct(Product product) {
        if (product == null) {
            closeEditor();
        } else {
            productForm.setProduct(product);
            productForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        productForm.setProduct(null);
        productForm.setVisible(false);
        removeClassName("editing");
    }

    private void addProduct() {
        grid.asSingleSelect().clear();
        editProduct(new Product());
        productForm.setVisible(true);
    }

    private void updateList() {

        grid.setItems(productService.findAllProducts(filter.getValue()));
    }

    private void configureGrid() {
        grid.addClassName("product-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name", "description", "price");
        grid.addColumn(product -> product.getStatus().getName()).setHeader("Status");
        grid.addColumn(product -> product.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(p ->p.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editProduct(event.getValue()));
    }

    private void configureProductForm() {
        productForm = new ProductForm(productService.findAllStatuses(), productService.findAllCompanies());
        productForm.setWidth("25em");

        productForm.addListener(ProductForm.SaveEvent.class, this::saveProduct);
        productForm.addListener(ProductForm.DeleteEvent.class, this::deleteProduct);
        productForm.addListener(ProductForm.CancelEvent.class, e -> closeEditor());
    }

    private void saveProduct(ProductForm.SaveEvent event) {
        productService.saveProduct(event.getProduct());
        updateList();
        closeEditor();
    }

    private void deleteProduct(ProductForm.DeleteEvent event) {
        productService.deleteProduct(event.getProduct());
        updateList();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, productForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, productForm);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private HorizontalLayout getToolbar() {
        filter.setPlaceholder("Filter by name..");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(event -> updateList());


        Button addProductButton = new Button("Add Product");
        addProductButton.addClassName("text-button");

        addProductButton.addClickListener(click -> addProduct());


        HorizontalLayout toolbar = new HorizontalLayout(filter, addProductButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

}
