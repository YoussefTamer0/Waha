package com.bookstore.waha.service;

import com.bookstore.waha.model.Customer;
import com.bookstore.waha.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public Customer register(Customer customer) {
        customer.setEmail(customer.getEmail().trim().toLowerCase());
        customer.setPassword(customer.getPassword().trim());

        if (repo.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered: " + customer.getEmail());
        }

        return repo.save(customer);
    }

    public Customer login(String email, String password) {
        Optional<Customer> customerOpt = repo.findByEmail(email.trim().toLowerCase());

        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer not found with email: " + email);
        }

        Customer customer = customerOpt.get();

        if (!customer.getPassword().equals(password.trim())) {
            throw new RuntimeException("Invalid password for email: " + email);
        }

        return customer;
    }

    public Customer update(Customer customer) {
        if (customer.getCustomerID() == null) {
            throw new RuntimeException("Customer ID cannot be null for update");
        }

        if (!repo.existsById(customer.getCustomerID())) {
            throw new RuntimeException("Customer not found with ID: " + customer.getCustomerID());
        }

        return repo.save(customer);
    }
}

