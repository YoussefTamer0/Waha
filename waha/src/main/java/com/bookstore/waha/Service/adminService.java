package com.bookstore.waha.service;

import com.bookstore.waha.model.Admin;
import com.bookstore.waha.model.Book;
import com.bookstore.waha.repository.AdminRepository;
import com.bookstore.waha.repository.BookRepository;
import com.bookstore.waha.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AdminService {
    private final AdminRepository adminrepo;
    private final BookRepository bookrepo;
    private final OrderRepository orderrepo;

    public AdminService(AdminRepository adminrepo, BookRepository bookrepo, OrderRepository orderrepo) {
        this.adminrepo = adminrepo;
        this.bookrepo=bookrepo;
        this.orderrepo=orderrepo;

    }

    public void addAdmin(Admin admin) {

        if (admin.getAdminID() != null && adminrepo.existsById(admin.getAdminID())) {
            throw new RuntimeException("Admin with ID " + admin.getAdminID() + " already exists");
        }
        adminrepo.save(admin);
    }

    public void removeAdmin(Admin admin) {

        if (admin.getAdminID() == null || !adminrepo.existsById(admin.getAdminID())) {
            throw new RuntimeException("Admin doesnt exist");
        }
        adminrepo.delete(admin);
    }

    public List<Admin> getAllAdmin() {
        return adminrepo.findAll();
    }

    public Admin findAdminByID(Integer ID) {
        if (ID == null) {
            throw new RuntimeException("ID cannot be null");
        }

        Optional<Admin> adminOptional = adminrepo.findById(ID);

        if (adminOptional.isPresent()) {
            return adminOptional.get();
        } else {
            throw new RuntimeException("Admin doesn't exist with ID: " + ID);
        }
    }
    public void addBook(Book book) {

        if (book.getBookID() != null && bookrepo.existsById(book.getBookID())) {
            throw new RuntimeException("Book already exists with ID: " + book.getBookID());
        }
        bookrepo.save(book);
    }

    public void deleteBook(Book book) {
        if (book.getBookID() == null || !bookrepo.existsById(book.getBookID())) {
            throw new RuntimeException("Book doesnt exist");
        }
        bookrepo.delete(book);
    }
    public Book findBookByID(Long ID) {

        return bookrepo.findById(ID).orElse(null);
    }
    public Admin login(String email, String password) {
        Admin admin = adminrepo.findByEmail(email);

        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }
    public void clearOrder(Long ID) {

        if (ID == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }


        if (!orderrepo.existsById(ID)) {
            throw new RuntimeException("Order not found with ID: " + ID);
        }

        try {
            orderrepo.deleteById(ID);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete order with ID: " + ID + " - " + e.getMessage(), e);
        }
    }
}