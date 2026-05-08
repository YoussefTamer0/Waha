package com.bookstore.waha.Service;

import com.bookstore.waha.model.CartItem;
import com.bookstore.waha.model.Order;
import com.bookstore.waha.model.OrderItem;
import com.bookstore.waha.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(Order order, HttpSession session) {


        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");

        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();

        double total = 0;


        for (CartItem cartItem : cartItems) {

            OrderItem orderItem = new OrderItem();

            orderItem.setBook(cartItem.getBook());

            orderItem.setQuantity(cartItem.getQuantity());

            orderItem.setPrice(cartItem.getBook().getPrice());

            orderItem.setOrder(order);

            total += orderItem.getPrice() * orderItem.getQuantity();

            orderItems.add(orderItem);
        }

        order.setItems(orderItems);

        order.setTotalPrice(total);


        Order savedOrder = orderRepository.save(order);

        session.removeAttribute("cartItems");

        return savedOrder;
    }

    public void deleteOrder(Long orderId) {

        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }

        orderRepository.deleteById(orderId);
    }

    public long countOrders() {
        return orderRepository.count();
    }

    public double getTotalRevenue() {
        return orderRepository.findAll().stream()
                .mapToDouble(order -> order.getTotalPrice() != null ? order.getTotalPrice() : 0)
                .sum();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> searchOrders(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllOrders();
        }
        return orderRepository.findAll().stream()
                .filter(order -> (order.getCustomerName() != null &&
                        order.getCustomerName().toLowerCase().contains(keyword.toLowerCase())) ||
                        (order.getAddress() != null &&
                                order.getAddress().toLowerCase().contains(keyword.toLowerCase())))
                .toList();
    }

}