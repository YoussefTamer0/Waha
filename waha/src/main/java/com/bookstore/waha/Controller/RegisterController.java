package com.bookstore.waha.Controller;

import com.bookstore.waha.Service.CustomerService;
import com.bookstore.waha.Service.EmailService;
import com.bookstore.waha.Model.Customer;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final CustomerService customerService;
    private final EmailService emailService;

    public RegisterController(CustomerService customerService, EmailService emailService) {
        this.customerService = customerService;
        this.emailService = emailService;
    }

    @GetMapping("/customer/register")
    public String showForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/register";
    }

    @PostMapping("/customer/register")
    public String addCustomer(@Valid @ModelAttribute("customer") Customer customer,
                              BindingResult result,
                              Model model) {


        if (result.hasErrors()) {
            return "customers/register";
        }

        Customer registeredCustomer = customerService.register(customer);

        if (registeredCustomer == null) {
            model.addAttribute("message", "Email already exists. Please use a different email.");
            return "customers/register";
        }

        String subject = "Welcome to Novella Bookstore!";
        String body = "Dear " + registeredCustomer.getFirstName() + ",\n\nThank you for registering at Novella Bookstore. We are excited to have you!\n\nHappy reading!\n\nThe Novella Bookstore Team";
        emailService.sendEmail(registeredCustomer.getEmail(), subject, body);

        return "redirect:/customer/login";
    }
}
