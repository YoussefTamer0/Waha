package com.bookstore.waha.Repository;

import com.bookstore.waha.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
