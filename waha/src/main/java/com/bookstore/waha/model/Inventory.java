package com.bookstore.waha.model;

import com.bookstore.waha.model.Book;
import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "bookID", nullable = false)
    private Book book;

    private int quantity;
    private int minStockLevel;

    public Inventory() {}

    public Inventory(Book book, int quantity, int minStockLevel) {
        this.book = book;
        this.quantity = quantity;
        this.minStockLevel = minStockLevel;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinStockLevel() {
        return minStockLevel;
    }
    public void setMinStockLevel(int minStockLevel) {
        this.minStockLevel = minStockLevel;
    }
}