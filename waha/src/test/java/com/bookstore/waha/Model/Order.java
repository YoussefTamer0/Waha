package com.bookstore.waha.Model;
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







}
