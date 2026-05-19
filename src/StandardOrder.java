package com.example.onlinebookstore.model;

import java.time.LocalDateTime;

/**
 * Concrete subclass representing a Standard Order.
 */
public class StandardOrder extends Order {

    public StandardOrder() {
        super();
    }

    public StandardOrder(String orderId, String userId, String customerName, double total, String status, LocalDateTime orderDate, String itemsSummary) {
        super(orderId, userId, customerName, total, status, orderDate, itemsSummary);
    }

    @Override
    public double calculateFinalTotal() {
        return getTotal(); // No extra fees
    }

    @Override
    public String getOrderType() {
        return "Standard";
    }
}
