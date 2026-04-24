package com.bookstore.waha.Repository;

import com.bookstore.waha.Model.Order;
import com.bookstore.waha.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
