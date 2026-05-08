package com.bookstore.waha.Controller;
import com.bookstore.waha.model.CartItem;
import com.bookstore.waha.model.Order;
import com.bookstore.waha.Service.OrderService;
import jakarta.servlet.http.HttpSession;
import com.bookstore.waha.model.Order;
import com.bookstore.waha.Service.OrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/Checkout")
    public String checkoutPage(Model model) {
        model.addAttribute("order", new Order());
        return "Checkout";
    }

    @PostMapping("/place")
    public String placeOrder(@Valid @ModelAttribute("order") Order order,
                             BindingResult result,
                             HttpSession session,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        // 1. Validate form input
        if (result.hasErrors()) {
            return "Checkout";
        }

        // 2. Validate cart is not empty
        @SuppressWarnings("unchecked")
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");

        if (cartItems == null || cartItems.isEmpty()) {
            model.addAttribute("cartError", "Your cart is empty");
            return "Checkout";
        }

        // 3. Validate cart has items with valid quantities
        boolean hasValidItems = cartItems.stream().allMatch(item -> item.getQuantity() > 0);
        if (!hasValidItems) {
            model.addAttribute("cartError", "Cart contains invalid quantities");
            return "Checkout";
        }

        try {
            // 4. Place order
            Order savedOrder = orderService.placeOrder(order, session);

            // 5. Clear cart after successful order
            session.removeAttribute("cartItems");

            // 6. Success message
            redirectAttributes.addFlashAttribute("success", "Order placed successfully!");
            model.addAttribute("order", savedOrder);

            return "redirect:/orders/confirmation";

        } catch (Exception e) {
            model.addAttribute("error", "Failed to place order: " + e.getMessage());
            return "Checkout";
        }
    }

    @GetMapping("/confirmation")
    public String confirmation() {
        return "Confirmation";
    }
}