package com.example.onlinebookstore.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Abstract Base Class representing a Customer Order.
 */
public abstract class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderId;
    private String userId;
    private String customerName;
    private double total; // Base total
    private String status; // ONGOING, DELIVERED, ERROR, CANCELLED
    private LocalDateTime orderDate;
    private String itemsSummary;

    public Order() {}

    public Order(String orderId, String userId, String customerName, double total, String status, LocalDateTime orderDate, String itemsSummary) {
        this.orderId = orderId;
        this.userId = userId;
        this.customerName = customerName;
        this.total = total;
        this.status = status;
        this.orderDate = orderDate;
        this.itemsSummary = itemsSummary;
    }

    // --- Common Getters and Setters ---

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getItemsSummary() { return itemsSummary; }
    public void setItemsSummary(String itemsSummary) { this.itemsSummary = itemsSummary; }

    /**
     * Polymorphic method to calculate the final amount including fees.
     */
    public abstract double calculateFinalTotal();

    /**
     * Get the descriptive order type.
     */
    public abstract String getOrderType();
}
