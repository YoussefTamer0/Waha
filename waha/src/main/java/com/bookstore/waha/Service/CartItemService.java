package com.bookstore.waha.service;
import com.bookstore.waha.model.CartItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemService {

    public void addItem(List<CartItem> cart, CartItem newItem) {

        for (CartItem item : cart) {
            if (item.getBook().getBookID().equals(newItem.getBook().getBookID())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }

        cart.add(newItem);
    }

    public void removeItem(List<CartItem> cart, Integer bookID) {
        cart.removeIf(item ->
                item.getBook().getBookID().equals(bookID)
        );
    }

    public double calculateTotal(List<CartItem> cart) {
        double total = 0;

        for (CartItem item : cart) {
            total += item.getBook().getPrice() * item.getQuantity();
        }

        return total;
    }

    public List<CartItem> initCart(List<CartItem> cart) {
        if (cart == null) {
            return new ArrayList<>();
        }
        return cart;
    }
}
