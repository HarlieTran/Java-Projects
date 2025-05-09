package com.example.application.views.list;

import com.example.application.data.CartItem;
import com.example.application.layout.UserView;
import com.example.application.services.CartService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Cart | E-Commerce")
@Route(value = "cart", layout = UserView.class)
@RolesAllowed("USER")
public class CartView extends VerticalLayout {

    private final CartService cartService;
    private final Grid<CartItem> grid = new Grid<>(CartItem.class, false);
    private final Span totalPrice = new Span();

    @Autowired
    public CartView(CartService cartService) {
        this.cartService = cartService;

        setPadding(true);
        setSpacing(true);
        setSizeFull();

        // Configure the grid
        configureGrid();

        // Add components to the layout
        add(grid, totalPrice, createShoppingAndCheckoutButton());

        // Update the cart display
        updateCart();
    }

    private void configureGrid() {
        // Add image column
        grid.addComponentColumn(item -> {
            Image image = new Image(
                    item.getProduct().getImageUrl() != null ? item.getProduct().getImageUrl() : "https://via.placeholder.com/50",
                    item.getProduct().getName()
            );
            image.setWidth("50px");
            image.setHeight("50px");
            image.addClassName("product-image");

            return image;
        }).setHeader("Image");

        // Add other columns
        grid.addColumn(item -> item.getProduct().getName()).setHeader("Product");
        grid.addColumn(item -> item.getProduct().getPrice()).setHeader("Price");

        // Quantity Editor (inline editing)
        grid.addComponentColumn(item -> {
            IntegerField quantityField = new IntegerField();
            quantityField.setValue(item.getQuantity());
            quantityField.setWidth("100px");
            quantityField.addValueChangeListener(event -> {
                if(event.getValue() != null && event.getValue() > 0) {
                    cartService.updateQuantity(item.getProduct(),event.getValue());
                    updateCart();
                }
            });
            return quantityField;
        }).setHeader("Quantity");

        grid.addColumn(CartItem::getTotalPrice).setHeader("Total");

        grid.addComponentColumn(item -> {
            Button removeButton = new Button("Remove", click -> {
                cartService.removeFromCart(item.getProduct());
                updateCart();
            });
            removeButton.addClassName("text-button");
            return removeButton;
        }).setHeader("Actions");
    }

    private void updateCart() {
        grid.setItems(cartService.getCartItems());
        totalPrice.setText("Total Price: $" + cartService.getTotalPrice());
    }

    private HorizontalLayout createShoppingAndCheckoutButton() {
        Button checkoutButton = new Button("Proceed to Checkout", event -> {
            // Handle checkout logic
            Notification.show("Checkout not implemented yet", 3000, Notification.Position.TOP_CENTER);
        });
        checkoutButton.addClassName("text-button");

        Button continueShoppingButton = new Button("Continue Shopping", event -> {
            getUI().ifPresent(ui -> ui.navigate("products"));
        });
        continueShoppingButton.addClassName("text-button");

        HorizontalLayout cartProcessLayout = new HorizontalLayout(continueShoppingButton, checkoutButton);
        cartProcessLayout.getStyle().set("margin-top", "1em");

        return cartProcessLayout;
    }
}