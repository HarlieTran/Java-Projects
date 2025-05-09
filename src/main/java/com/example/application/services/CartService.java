package com.example.application.services;

import com.example.application.data.CartItem;
import com.example.application.data.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final List<CartItem> cartItems = new ArrayList<>();

    public void addToCart(Product product, int quantity) {
        Optional<CartItem> existingItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update quantity if the product is already in the cart
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            // Add new item to the cart
            cartItems.add(new CartItem(product, quantity));
        }
    }

    public void removeFromCart(Product product) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(product.getId()));
    }

    public void updateQuantity(Product product, int quantity) {
        cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotalPrice() {
        return cartItems.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    public void clearCart() {
        cartItems.clear();
    }
}