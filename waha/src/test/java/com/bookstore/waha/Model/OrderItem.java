package com.bookstore.waha.Model;
import jakarta.persistence.*;

import jakarta.persistence.Entity;

@Entity

public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

//    @ManyToOne
//    private Book book; i will make it as comment until book class created




}
