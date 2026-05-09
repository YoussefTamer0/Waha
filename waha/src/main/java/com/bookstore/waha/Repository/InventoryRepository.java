package com.bookstore.waha.Repository;

import com.bookstore.waha.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByBook_BookID(Long bookId);}