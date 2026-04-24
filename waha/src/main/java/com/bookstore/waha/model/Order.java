package com.bookstore.waha.model;
import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "orders")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String address;
    private Double totalPrice;
    @OneToMany(mappedBy ="order",cascade = CascadeType.ALL)
    private List<OrderItem> items;

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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
