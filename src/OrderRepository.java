package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.ExpressOrder;
import com.example.onlinebookstore.model.Order;
import com.example.onlinebookstore.model.StandardOrder;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    private final String filePath = "data/orders.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public OrderRepository() {
        ensureFileExists();
    }

    private void ensureFileExists() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                
                Order order;
                // Handle Migration (Legacy had no type prefix, parts[0] was ORD...)
                if (!parts[0].equals("STANDARD") && !parts[0].equals("EXPRESS")) {
                    if (parts.length >= 7) {
                        order = new StandardOrder(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4], LocalDateTime.parse(parts[5], formatter), parts[6]);
                        orders.add(order);
                    }
                    continue;
                }

                // New Polymorphic Format: TYPE|id|userId|name|total|status|date|summary
                if (parts.length >= 8) {
                    String type = parts[0];
                    String id = parts[1];
                    String userId = parts[2];
                    String name = parts[3];
                    double total = Double.parseDouble(parts[4]);
                    String status = parts[5];
                    LocalDateTime date = LocalDateTime.parse(parts[6], formatter);
                    String summary = parts[7];

                    if ("EXPRESS".equals(type)) {
                        order = new ExpressOrder(id, userId, name, total, status, date, summary);
                    } else {
                        order = new StandardOrder(id, userId, name, total, status, date, summary);
                    }
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public synchronized void save(Order order) {
        List<Order> orders = findAll();
        
        if (order.getOrderId() == null || order.getOrderId().isEmpty()) {
            order.setOrderId(generateNextId(orders));
        }

        boolean found = false;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId().equals(order.getOrderId())) {
                orders.set(i, order);
                found = true;
                break;
            }
        }
        if (!found) orders.add(order);
        
        writeAll(orders);
    }

    private String generateNextId(List<Order> currentOrders) {
        int maxId = 1000;
        for (Order o : currentOrders) {
            if (o.getOrderId().startsWith("ORD")) {
                try {
                    int idVal = Integer.parseInt(o.getOrderId().substring(3));
                    if (idVal > maxId) maxId = idVal;
                } catch (NumberFormatException ignored) {}
            }
        }
        return "ORD" + (maxId + 1);
    }

    private void writeAll(List<Order> orders) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Order order : orders) {
                String type = order.getOrderType().toUpperCase();
                writer.write(String.format("%s|%s|%s|%s|%.2f|%s|%s|%s",
                        type,
                        order.getOrderId(),
                        order.getUserId(),
                        order.getCustomerName(),
                        order.getTotal(),
                        order.getStatus(),
                        order.getOrderDate().format(formatter),
                        order.getItemsSummary()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
