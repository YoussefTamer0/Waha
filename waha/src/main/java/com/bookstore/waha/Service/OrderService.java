package com.bookstore.waha.Service;
import com.bookstore.waha.Model.CartItem;
import com.bookstore.waha.Model.Customer;
import com.bookstore.waha.Model.Inventory;
import com.bookstore.waha.Model.Order;
import com.bookstore.waha.Model.OrderItem;
import com.bookstore.waha.Repository.InventoryRepository;
import com.bookstore.waha.Repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;

    public OrderService(OrderRepository orderRepository, InventoryRepository inventoryRepository) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public Order placeOrder(Order order, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("loggedCustomer");
        if (customer == null) {
            throw new RuntimeException("You must be logged in to place an order");
        }

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

            // Decrement inventory stock
            Optional<Inventory> inventoryOpt = inventoryRepository.findByBook_BookID(cartItem.getBook().getBookID());
            inventoryOpt.ifPresent(inv -> {
                int newQty = inv.getQuantity() - cartItem.getQuantity();
                inv.setQuantity(Math.max(newQty, 0)); // don't go negative
                inventoryRepository.save(inv);
            });
        }

        order.setItems(orderItems);
        order.setTotalPrice(total);
        order.setCustomer(customer);
        order.setStatus("Pending");

        Order savedOrder = orderRepository.save(order);

        // Clear the cart from session
        session.removeAttribute("cartItems");

        // Store the saved order in session so confirmation page can show it
        session.setAttribute("lastOrder", savedOrder);

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

    public List<Order> getOrdersByCustomer(Customer customer) {
        return orderRepository.findByCustomerOrderByOrderDateDesc(customer);
    }
}