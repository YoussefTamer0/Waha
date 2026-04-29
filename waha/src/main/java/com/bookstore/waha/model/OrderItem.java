package com.bookstore.waha.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "OrderItem")

public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Min(1)
    private int quantity;
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

  @ManyToOne
  private books book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public books getBook() {
        return book;
    }

    public void setBook(books book) {
        this.book = book;
    }
}

