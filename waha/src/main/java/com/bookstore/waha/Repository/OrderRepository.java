package com.bookstore.waha.Repository;
import com.bookstore.waha.Model.Customer;
import com.bookstore.waha.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends  JpaRepository<Order, Long> {

    List<Order> findByCustomerOrderByOrderDateDesc(Customer customer);


}
