package com.example.onlinebookstore.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Admin {
    private String username;
    private String password;
    private String role;
    private String fullName;
    private String email;

    public Admin() {}

    public Admin(String username, String password, String role) {
        this(username, password, role, "N/A", "N/A");
    }

    public Admin(String username, String password, String role, String fullName, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName != null ? fullName : "N/A";
        this.email = email != null ? email : "N/A";
    }

    public abstract String getAdminRole();

    public List<String> getPermissions() {
        return new ArrayList<>(Arrays.asList("READ_ADMINS", "READ_LOGS"));
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
