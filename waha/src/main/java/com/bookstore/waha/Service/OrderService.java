package com.bookstore.waha.Service;

import com.bookstore.waha.model.Order;
import com.bookstore.waha.model.OrderItem;
import com.bookstore.waha.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
/*
@Service

public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    public Order placeOrder(Order order) {

        List<OrderItem> items = cartService.getItems();


        if (items == null || items.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        order.setItems(items);

        double total = 0;

        for (OrderItem item : items) {


            item.setOrder(order);


            if (item.getBook() == null) {
                throw new RuntimeException("Book not found");
            }

            double price = item.getBook().getPrice();
            item.setPrice(price);


            total += price * item.getQuantity();
        }

        order.setTotalPrice(total);


        return orderRepository.save(order);
    }
}

 */