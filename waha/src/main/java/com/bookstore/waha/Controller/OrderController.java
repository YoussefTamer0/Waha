package com.bookstore.waha.Controller;
import com.bookstore.waha.Service.OrderService;
import org.springframework.stereotype.Controller;

@Controller

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }



}
