package com.bookstore.waha.Controller;
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

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/Checkout")
    public String showCheckoutForm(Model model) {
        model.addAttribute("order", new Order());
        return "orders/Checkout";
    }

    @PostMapping("/place")
    public String placeOrder(@Valid @ModelAttribute Order order, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "orders/Checkout";
        }

        Order savedOrder = orderService.placeOrder(order);

        model.addAttribute("order", savedOrder);

        return "orders/Confirmation";
    }
}
