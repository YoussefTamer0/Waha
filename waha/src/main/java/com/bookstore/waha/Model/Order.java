package com.bookstore.waha.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Customer name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(name = "customer_name")
    private String customerName;

    @NotBlank(message = "Address is required")
    @Size(min = 5, message = "Address must be at least 5 characters")
    private String address;

    @NotNull(message = "Total price is required")
    @Positive(message = "Total price must be positive")
    private Double totalPrice;

    // Order status (Pending, Shipped, Delivered, etc.)
    @Column(nullable = false)
    private String status = "Pending";

    // Automatically stores the date and time when the order is created
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItem> items;

    public Order(Long id,
                 String customerName,
                 String address,
                 Double totalPrice,
                 String status,
                 LocalDateTime orderDate,
                 List<OrderItem> items) {

        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = orderDate;
        this.items = items;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @NotEmpty(message = "Order must contain at least one item")
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}