package com.bookstore.waha.repository;


import com.bookstore.waha.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface adminRepository extends JpaRepository<Admin, Integer> {
}
