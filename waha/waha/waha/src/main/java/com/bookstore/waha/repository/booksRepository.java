package com.bookstore.waha.repository;

import com.bookstore.waha.model.books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface booksRepository extends JpaRepository<books, Integer> {

}
