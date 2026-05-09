package com.bookstore.waha.Repository;


import com.bookstore.waha.Model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishersRepository extends JpaRepository<Publisher, Long> {
}
