package com.bookstore.waha.Service;

import com.bookstore.waha.Model.Customer;
import com.bookstore.waha.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public Customer register(Customer customer) {
        try {

            // Remove extra spaces and make email lowercase
            customer.setEmail(customer.getEmail().trim().toLowerCase());

            // Remove extra spaces from password
            customer.setPassword(customer.getPassword().trim());

            // Check if email already exists
            if (repo.findByEmail(customer.getEmail()).isPresent()) {
                throw new RuntimeException(
                        "Email already registered: " + customer.getEmail()
                );
            }

            // Save customer in database
            return repo.save(customer);

        } catch (Exception e) {

            // Print error in console
            System.out.println("Error during registration: " + e.getMessage());

            // Re-throw exception
            throw new RuntimeException("Registration failed", e);
        }
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

