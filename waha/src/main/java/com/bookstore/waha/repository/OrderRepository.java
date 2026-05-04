package com.bookstore.waha.repository;
import com.bookstore.waha.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends  JpaRepository<Order, Long> {

    @Override
    Optional<Order> findById(Long id);
}
