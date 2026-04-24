package com.bookstore.waha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bookstore.waha.model.publishers;



public interface publishersRepository extends JpaRepository<publishers, Integer> {

}
