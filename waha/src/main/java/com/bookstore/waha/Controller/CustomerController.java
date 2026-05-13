package com.bookstore.waha.Controller;

import com.bookstore.waha.Model.Admin;
import com.bookstore.waha.Service.CustomerService;
import com.bookstore.waha.Model.Customer;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private static final String REMEMBER_ME_COOKIE = "novella_remember_email";
    private static final int COOKIE_MAX_AGE_DAYS = 30;

    private final CustomerService customerService;
    private final com.bookstore.waha.Service.AdminService adminService;

    public CustomerController(CustomerService customerService, com.bookstore.waha.Service.AdminService adminService) {
        this.customerService = customerService;
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String showLogin(HttpServletRequest request, Model model) {
        // Read the remember-me cookie and pre-fill the email field if present
        String rememberedEmail = getRememberMeCookieValue(request);
        if (rememberedEmail != null && !rememberedEmail.isEmpty()) {
            model.addAttribute("rememberedEmail", rememberedEmail);
        }
        return "customers/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(value = "rememberMe", required = false) String rememberMe,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        // Handle the remember-me cookie: set or clear it before attempting login
        if ("true".equals(rememberMe)) {
            // Set cookie valid for 30 days
            Cookie cookie = new Cookie(REMEMBER_ME_COOKIE, email);
            cookie.setMaxAge(COOKIE_MAX_AGE_DAYS * 24 * 60 * 60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);  // Not accessible via JavaScript for security
            response.addCookie(cookie);
        } else {
            // User unchecked "Remember Me" — delete any existing cookie
            Cookie cookie = new Cookie(REMEMBER_ME_COOKIE, "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        // Check admin FIRST so admin accounts always go to the dashboard
        try {
            Admin admin = this.adminService.login(email, password);
            if (admin != null) {
                session.setAttribute("loggedAdmin", admin);
                session.setAttribute("userType", "ADMIN");
                return "redirect:/admin/dashboard";
            }
        } catch (RuntimeException e) {
        }

        try {
            Customer customer = this.customerService.login(email, password);
            if (customer != null) {
                session.setAttribute("loggedCustomer", customer);
                session.setAttribute("userType", "CUSTOMER");
                return "redirect:/home";
            }
        } catch (RuntimeException e) {
        }

        model.addAttribute("error", "Invalid credentials");
        // Re-populate email so the field stays filled on error
        model.addAttribute("rememberedEmail", "true".equals(rememberMe) ? email : null);
        return "customers/login";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        String userType = (String) session.getAttribute("userType");

        if ("CUSTOMER".equals(userType)) {
            Customer customer = (Customer) session.getAttribute("loggedCustomer");
            if (customer == null) {
                return "redirect:/customer/login";
            } else {
                model.addAttribute("customer", customer);
                model.addAttribute("editMode", false);
                return "customers/Profile";
            }
        } else if ("ADMIN".equals(userType)) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/customer/login";
        }
    }

    @GetMapping("/profile/edit")
    public String editProfile(HttpSession session, Model model) {
        String userType = (String) session.getAttribute("userType");

        if ("CUSTOMER".equals(userType)) {
            Customer customer = (Customer) session.getAttribute("loggedCustomer");
            if (customer == null) {
                return "redirect:/customer/login";
            } else {
                model.addAttribute("customer", customer);
                model.addAttribute("editMode", true);
                return "customers/Profile";
            }
        } else {
            return "redirect:/customer/login";
        }
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute Customer updatedCustomer, HttpSession session, Model model) {
        String userType = (String) session.getAttribute("userType");

        if ("CUSTOMER".equals(userType)) {
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
                    this.customerService.update(sessionCustomer);
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
        } else {
            return "redirect:/customer/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/customer/login";
    }

    // ── Helper ──────────────────────────────────────────────────────────────

    /**
     * Reads the remember-me cookie from the incoming request.
     * Returns null if the cookie is absent or blank.
     */
    private String getRememberMeCookieValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (REMEMBER_ME_COOKIE.equals(cookie.getName())) {
                    String value = cookie.getValue();
                    return (value != null && !value.isEmpty()) ? value : null;
                }
            }
        }
        return null;
    }
}