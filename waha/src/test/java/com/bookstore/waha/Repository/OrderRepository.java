package com.bookstore.waha.Repository;
import com.bookstore.waha.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends  JpaRepository<Order, Long> {



}
