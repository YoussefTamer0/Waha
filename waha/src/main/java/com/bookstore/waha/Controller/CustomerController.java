package com.bookstore.waha.Controller;

import com.bookstore.waha.Service.CustomerService;
import com.bookstore.waha.model.Customer;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {

        private final CustomerService service;

        public CustomerController(CustomerService service) {
            this.service = service;
        }

        @GetMapping("/register")
        public String showRegister(Model model) {
            model.addAttribute("customer", new Customer());
            return "customers/register";
        }

        @PostMapping("/register")
        public String register(@ModelAttribute Customer customer, Model model) {
            Customer saved = this.service.register(customer);
            if (saved == null) {
                model.addAttribute("error", "Email already registered");
                return "customers/register";
            } else {
                return "redirect:/customer/login";
            }
        }

        @GetMapping("/login")
        public String showLogin() {
            return "customers/login";
        }

        @PostMapping("/login")
        public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
            Customer customer = this.service.login(email, password);
            if (customer == null) {
                model.addAttribute("error", "Invalid credentials");
                return "customers/login";
            } else {
                session.setAttribute("loggedCustomer", customer);
                return "redirect:/customer/profile";
            }
        }

        @GetMapping("/profile")
        public String profile(HttpSession session, Model model) {
            Customer customer = (Customer) session.getAttribute("loggedCustomer");
            if (customer == null) {
                return "redirect:/customer/login";
            } else {
                model.addAttribute("customer", customer);
                model.addAttribute("editMode", false);
                return "customers/Profile";
            }
        }

        @GetMapping("/profile/edit")
        public String editProfile(HttpSession session, Model model) {
            Customer customer = (Customer) session.getAttribute("loggedCustomer");
            if (customer == null) {
                return "redirect:/customer/login";
            } else {
                model.addAttribute("customer", customer);
                model.addAttribute("editMode", true);
                return "customers/Profile";
            }
        }

        @PostMapping("/profile/update")
        public String updateProfile(@ModelAttribute Customer updatedCustomer, HttpSession session, Model model) {
            Customer sessionCustomer = (Customer) session.getAttribute("loggedCustomer");
            if (sessionCustomer == null) {
                return "redirect:/customer/login";
            } else {
                try {
                    sessionCustomer.setFirstName(updatedCustomer.getFirstName());
                    sessionCustomer.setLastName(updatedCustomer.getLastName());
                    sessionCustomer.setStreetNumber(updatedCustomer.getStreetNumber());
                    sessionCustomer.setStreetName(updatedCustomer.getStreetName());
                    sessionCustomer.setPostalCode(updatedCustomer.getPostalCode());
                    sessionCustomer.setProvince(updatedCustomer.getProvince());
                    sessionCustomer.setCountry(updatedCustomer.getCountry());
                    sessionCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
                    this.service.update(sessionCustomer);
                    session.setAttribute("loggedCustomer", sessionCustomer);
                    model.addAttribute("success", "Profile updated successfully");
                    model.addAttribute("editMode", false);
                    model.addAttribute("customer", sessionCustomer);
                    return "customers/Profile";
                } catch (Exception e) {
                    model.addAttribute("error", "Update failed: " + e.getMessage());
                    model.addAttribute("editMode", true);
                    model.addAttribute("customer", updatedCustomer);
                    return "customers/Profile";
                }
            }
        }

        @GetMapping("/logout")
        public String logout(HttpSession session) {
            session.invalidate();
            return "redirect:/customer/login";
        }
    }

