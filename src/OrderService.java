package com.example.onlinebookstore.service;

import com.example.onlinebookstore.model.Order;
import com.example.onlinebookstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void placeOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        Collections.reverse(orders); // Show newest first
        return orders;
    }

    public List<Order> getOrdersByUser(String userId) {
        return orderRepository.findAll().stream()
                .filter(o -> o.getUserId().equalsIgnoreCase(userId))
                .sorted((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()))
                .collect(Collectors.toList());
    }

    public List<Order> searchOrders(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllOrders();
        }
        String lowerQuery = query.toLowerCase().trim();
        return orderRepository.findAll().stream()
                .filter(o -> o.getOrderId().toLowerCase().contains(lowerQuery) ||
                             o.getUserId().toLowerCase().contains(lowerQuery) ||
                             o.getCustomerName().toLowerCase().contains(lowerQuery))
                .sorted((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()))
                .collect(Collectors.toList());
    }

    public void updateOrderStatus(String orderId, String status) {
        orderRepository.findAll().stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst()
                .ifPresent(o -> {
                    o.setStatus(status);
                    orderRepository.save(o);
                });
    }

    public Optional<Order> getOrderById(String orderId) {
        return orderRepository.findAll().stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst();
    }

    public void cancelOrder(String orderId) {
        getOrderById(orderId).ifPresent(order -> {
            if ("ONGOING".equals(order.getStatus())) {
                order.setStatus("CANCELLED");
                orderRepository.save(order);
            }
        });
    }
}
