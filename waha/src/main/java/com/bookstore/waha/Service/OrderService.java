package com.bookstore.waha.Service;

import com.bookstore.waha.model.Order;
import com.bookstore.waha.model.OrderItem;
import com.bookstore.waha.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // Remove @NoArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(Order order, List<OrderItem> items) {
        double total = 0;

        for (OrderItem item : items) {
            item.setOrder(order);
            total += item.getPrice() * item.getQuantity();
        }

        order.setItems(items);
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }
}