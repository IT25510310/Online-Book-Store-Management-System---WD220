package com.example.onlinebookstore.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityLog {
    private String username;
    private String action;
    private String timestamp;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ActivityLog() {}

    public ActivityLog(String username, String action) {
        this.username = username;
        this.action = action;
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    public ActivityLog(String username, String action, String timestamp) {
        this.username = username;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
