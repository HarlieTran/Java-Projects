package com.example.application.views.list;

import com.example.application.data.Product;
import com.example.application.layout.UserView;
import com.example.application.services.CartService;
import com.example.application.services.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.stream.IntStream;

@PageTitle("Product Details | E-Commerce")
@Route(value = "product-details", layout = UserView.class)
@AnonymousAllowed
public class ProductDetailsView extends VerticalLayout implements HasUrlParameter<Long> {

    private final ProductService productService;
    private final CartService cartService;
    private final ComboBox<Integer> quantityComboBox = new ComboBox<>("Quantity");

    public ProductDetailsView(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;

        setPadding(true);
        setSpacing(true);
        setSizeFull();

        // Quote
        Span quote = new Span("\"Fragrance is to the nose what music is to the ear\"");
        quote.addClassName("quote");
        add(quote);

        // Initialize the quantity dropdown
        quantityComboBox.addClassName("quantity");
        quantityComboBox.setItems(IntStream.rangeClosed(1, 10).boxed().toList());
        quantityComboBox.setValue(1); // Default to 1

    }

    @Override
    public void setParameter(BeforeEvent event, Long productId) {
        // Fetch the product by ID
        Product product = productService.findProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Display product details
        displayProductDetails(product);
    }

    private void displayProductDetails(Product product) {


        // Product Image
        Image image = new Image(product.getImageUrl() != null ? product.getImageUrl() : "https://via.placeholder.com/300", product.getName());
        image.addClassName("product-image");

        // Product Information
        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.addClassName("product-info");
        infoLayout.setSpacing(true);
        infoLayout.setPadding(false);

        Span name = new Span(product.getName());
        name.addClassName("product-name");

        Span description = new Span(product.getDescription());
        description.addClassName("product-details");

        Span price = new Span("Price: $" + product.getPrice());
        price.addClassName("product-details");

        Span company = new Span("Company: " + product.getCompany().getName());
        company.addClassName("product-details");

        Span status = new Span("Status: " + product.getStatus().getName());
        status.addClassName("product-details");

        // Show quantity dropdown only if the product is in stock
        if ("In Stock".equals(product.getStatus().getName())) {
            quantityComboBox.setVisible(true);
        }

        // Buttons
        Button addToCartButton = new Button("Add to Cart", event -> {
            // Add 1 quantity to cart
            int quantity = quantityComboBox.getValue();
            cartService.addToCart(product, quantity);
            Notification.show("Added to cart: " + product.getName() + " (Quantity: " + quantity + ")"
                    , 3000, Notification.Position.TOP_CENTER);
        });
        addToCartButton.setClassName("cart-button");

        Button buyButton = new Button("Buy it Now", event -> {
            Notification.show(" This function will be implemented later!"
                    , 3000, Notification.Position.TOP_CENTER);
        });
        buyButton.setClassName("buy-button");


        infoLayout.add(name, description, price, company, status, quantityComboBox, addToCartButton, buyButton);

        // Image and Info Layout
        HorizontalLayout imageAndInfoLayout = new HorizontalLayout();
        imageAndInfoLayout.addClassName("image-and-info-layout");
        imageAndInfoLayout.setSpacing(true);
        imageAndInfoLayout.setAlignItems(Alignment.START);
        imageAndInfoLayout.add(image, infoLayout);

        // Add all components to the layout
        add(imageAndInfoLayout);
    }
}