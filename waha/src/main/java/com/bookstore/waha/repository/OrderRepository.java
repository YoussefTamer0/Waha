package com.bookstore.waha.repository;
import com.bookstore.waha.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends  JpaRepository<Order, Long> {



}
