package com.bookstore.waha.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// NOTE: CartItem is intentionally NOT a JPA @Entity — it is a transient/session-scoped
// in-memory object. There is no corresponding table in the database.
public class CartItem {

    @NotNull(message = "Book is required")
    private Book book;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    public CartItem() {}

    public CartItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
