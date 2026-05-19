package com.example.onlinebookstore.model;

import java.time.LocalDateTime;

/**
 * Concrete subclass representing an Express Order.
 * Adds a fixed express fee to the total.
 */
public class ExpressOrder extends Order {
    private static final double EXPRESS_FEE = 10.00;

    public ExpressOrder() {
        super();
    }

    public ExpressOrder(String orderId, String userId, String customerName, double total, String status, LocalDateTime orderDate, String itemsSummary) {
        super(orderId, userId, customerName, total, status, orderDate, itemsSummary);
    }

    @Override
    public double calculateFinalTotal() {
        return getTotal() + EXPRESS_FEE;
    }

    @Override
    public String getOrderType() {
        return "Express";
    }
}
