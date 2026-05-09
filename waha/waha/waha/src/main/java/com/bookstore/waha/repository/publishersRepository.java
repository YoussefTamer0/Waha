package com.bookstore.waha.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bookstore.waha.Model.publishers;



public interface publishersRepository extends JpaRepository<publishers, Integer> {

}
