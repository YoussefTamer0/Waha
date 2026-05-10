package com.bookstore.waha.Controller;

import com.bookstore.waha.Model.CartItem;
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

    @GetMapping({"", "/"})
    public String ordersRoot() {
        return "redirect:/orders/history";
    }

    @GetMapping("/Checkout")
    public String checkoutPage(Model model) {
        model.addAttribute("order", new Order());
        return "orders/Checkout";
    }

    @PostMapping("/place")
    public String placeOrder(@Valid @ModelAttribute("order") Order order,
                             BindingResult result,
                             HttpSession session,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "orders/Checkout";
        }

        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");

        if (cartItems == null || cartItems.isEmpty()) {
            model.addAttribute("cartError", "Your cart is empty");
            return "orders/Checkout";
        }

        boolean hasValidItems = cartItems.stream().allMatch(item -> item.getQuantity() > 0);
        if (!hasValidItems) {
            model.addAttribute("cartError", "Cart contains invalid quantities");
            return "orders/Checkout";
        }

        try {
            orderService.placeOrder(order, session);
            redirectAttributes.addFlashAttribute("success", "Order placed successfully!");
            return "redirect:/orders/confirmation";

        } catch (Exception e) {
            model.addAttribute("error", "Failed to place order: " + e.getMessage());
            return "orders/Checkout";
        }
    }

    @GetMapping("/confirmation")
    public String confirmation() {

        return "orders/Confirmation";
    }

    @GetMapping("/history")
    public String orderHistory(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders/orders";
    }

    @GetMapping("/search")
    public String searchOrders(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("orders", orderService.searchOrders(keyword));
        model.addAttribute("searchKeyword", keyword);
        return "orders/orders";
    }
}