package com.bookstore.waha.Model;

import jakarta.persistence.*;


@Entity
@Table(name = "orders")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;






}
