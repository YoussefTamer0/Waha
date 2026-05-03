package com.bookstore.waha.repository;

import com.bookstore.waha.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface customerRepository  extends JpaRepository<Customer,Long> {

}

