package com.bookstore.waha.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "bookID", nullable = false, unique = true)  // FIX: added unique=true to match DB UNIQUE constraint
    private Book book;

    @Column(name = "quantity")                  // FIX: added explicit @Column mappings
    private int quantity;

    @Column(name = "minStockLevel")             // FIX: added explicit @Column mapping
    private int minStockLevel;

    public Inventory() {}

    public Inventory(Book book, int quantity, int minStockLevel) {
        this.book = book;
        this.quantity = quantity;
        this.minStockLevel = minStockLevel;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }   // FIX: added missing setId()

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getMinStockLevel() { return minStockLevel; }
    public void setMinStockLevel(int minStockLevel) { this.minStockLevel = minStockLevel; }
}
