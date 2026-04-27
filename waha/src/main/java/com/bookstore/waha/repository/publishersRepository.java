package com.bookstore.waha.repository;


import com.bookstore.waha.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface publishersRepository extends JpaRepository<Publisher, Integer> {
}
