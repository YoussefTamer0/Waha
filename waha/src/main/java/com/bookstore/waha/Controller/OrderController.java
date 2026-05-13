package com.bookstore.waha.Controller;

import com.bookstore.waha.Model.CartItem;
import com.bookstore.waha.Model.Customer;
import com.bookstore.waha.Model.Order;
import com.bookstore.waha.Service.OrderService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /* =====================================================
       CHECKOUT PAGE
    ===================================================== */
    @GetMapping("/Checkout")
    public String checkoutPage(Model model, HttpSession session) {

        List<CartItem> cartItems =
                (List<CartItem>) session.getAttribute("cartItems");

        if (cartItems == null || cartItems.isEmpty()) {
            return "redirect:/cart/view";
        }

        double cartTotal = calculateCartTotal(cartItems);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("order", new Order());

        return "orders/Checkout";
    }


    /* =====================================================
       PLACE ORDER
    ===================================================== */
    @PostMapping("/place")
    public String placeOrder(
            @ModelAttribute("order") Order order,
            BindingResult result,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        List<CartItem> cartItems =
                (List<CartItem>) session.getAttribute("cartItems");

        if (cartItems == null || cartItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty");
            return "redirect:/cart/view";
        }

        // Manually validate only the fields the user fills in
        boolean hasErrors = false;
        if (order.getCustomerName() == null || order.getCustomerName().isBlank()) {
            result.rejectValue("customerName", "required", "Customer name is required");
            hasErrors = true;
        }
        if (order.getAddress() == null || order.getAddress().isBlank()) {
            result.rejectValue("address", "required", "Address is required");
            hasErrors = true;
        }

        if (hasErrors) {
            double cartTotal = calculateCartTotal(cartItems);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("cartTotal", cartTotal);
            return "orders/Checkout";
        }

        Customer loggedCustomer =
                (Customer) session.getAttribute("loggedCustomer");

        if (loggedCustomer == null) {
            redirectAttributes.addFlashAttribute("error", "Please login first");
            return "redirect:/customer/login";
        }

        try {
            orderService.placeOrder(order, session);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Order placed successfully!"
            );

            return "redirect:/orders/confirmation";

        } catch (Exception e) {

            double cartTotal = calculateCartTotal(cartItems);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("cartTotal", cartTotal);
            model.addAttribute("error",
                    "Failed to place order: " + e.getMessage());

            return "orders/Checkout";
        }
    }


    /* =====================================================
       ORDER CONFIRMATION
    ===================================================== */
    @GetMapping("/confirmation")
    public String confirmation(Model model, HttpSession session) {
        Order lastOrder = (Order) session.getAttribute("lastOrder");
        if (lastOrder != null) {
            model.addAttribute("order", lastOrder);
            session.removeAttribute("lastOrder"); // clean up
        }
        return "orders/Confirmation";
    }


    /* =====================================================
       ADMIN — ALL ORDERS
    ===================================================== */
    @GetMapping("/admin")
    public String adminOrderHistory(Model model) {

        model.addAttribute("orders",
                orderService.getAllOrders());

        return "orders/orders";
    }


    /* =====================================================
       ADMIN SEARCH
    ===================================================== */
    @GetMapping("/search")
    public String searchOrders(
            @RequestParam(required = false) String keyword,
            Model model) {

        model.addAttribute("orders",
                orderService.searchOrders(keyword));

        model.addAttribute("searchKeyword", keyword);

        return "orders/orders";
    }


    /* =====================================================
       CUSTOMER ORDER HISTORY
    ===================================================== */
    @GetMapping("/history")
    public String orderHistory(Model model, HttpSession session) {

        Customer loggedCustomer =
                (Customer) session.getAttribute("loggedCustomer");

        if (loggedCustomer == null) {
            return "redirect:/customer/login";
        }

        model.addAttribute(
                "orders",
                orderService.getOrdersByCustomer(loggedCustomer)
        );

        return "orders/history";
    }


    /* =====================================================
       HELPER METHOD
    ===================================================== */
    private double calculateCartTotal(List<CartItem> cartItems) {

        double total = 0;

        for (CartItem item : cartItems) {
            total += item.getBook().getPrice()
                    * item.getQuantity();
        }

        return total;
    }
}