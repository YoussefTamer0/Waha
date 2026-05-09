package com.bookstore.waha.Repository;

import com.bookstore.waha.Model.books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface booksRepository extends JpaRepository<books, Integer> {

}
