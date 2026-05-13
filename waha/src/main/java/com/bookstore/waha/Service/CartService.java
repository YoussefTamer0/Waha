package com.bookstore.waha.Service;

import com.bookstore.waha.Model.Cart;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    public Cart initCart(Cart cart) {
        if (cart == null) {
            return new Cart();
        }
        return cart;
    }

    public Cart getCartFromSession(Object sessionCart) {
        if (sessionCart instanceof Cart) {
            return (Cart) sessionCart;
        }
        return new Cart();
    }

    public int getTotalItems(Cart cart) {
        if (cart == null || cart.getItems() == null) {
            return 0;
        }
        return cart.getItemCount();
    }

    public double calculateTotal(Cart cart) {
        if (cart == null) {
            return 0;
        }
        return cart.getTotal();
    }

    public boolean isEmpty(Cart cart) {
        return cart == null || cart.isEmpty();
    }
}
