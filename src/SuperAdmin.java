package com.example.onlinebookstore.model;

import java.util.Arrays;
import java.util.List;

public class SuperAdmin extends Admin {
    
    public SuperAdmin() {
        super();
        setRole("SUPER_ADMIN");
    }

    public SuperAdmin(String username, String password, String fullName, String email) {
        super(username, password, "SUPER_ADMIN", fullName, email);
    }

    @Override
    public List<String> getPermissions() {
        List<String> permissions = super.getPermissions();
        permissions.addAll(Arrays.asList("MANAGE_ADMINS", "MANAGE_BOOKS"));
        return permissions;
    }

    @Override
    public String getAdminRole() {
        return "SUPER_ADMIN";
    }
}
