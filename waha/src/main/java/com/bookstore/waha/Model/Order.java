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
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Customer name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(name = "customer_name")
    private String customerName;

    @NotBlank(message = "Address is required")
    @Size(min = 5, message = "Address must be at least 5 characters")
    @Column(name = "address")
    private String address;

    @NotNull(message = "Total price is required")
    @Positive(message = "Total price must be positive")
    @Column(name = "totalPrice")  // matches DB column
    private Double totalPrice;

    @Column(name = "status", nullable = false)
    private String status = "Pending";

    @CreationTimestamp
    @Column(name = "orderDate", nullable = false, updatable = false)  // matches DB column
    private LocalDateTime orderDate;

    // FIX: removed @NotEmpty from the field — it causes validation failures when
    // items are added after the Order is first persisted. Validate at the service layer instead.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    public Order() {}

    public Order(Long id, String customerName, String address, Double totalPrice,
                 String status, LocalDateTime orderDate, List<OrderItem> items) {
        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = orderDate;
        this.items = items;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
