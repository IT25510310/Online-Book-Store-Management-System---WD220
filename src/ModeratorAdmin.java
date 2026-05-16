package com.example.onlinebookstore.model;

import java.util.List;

public class ModeratorAdmin extends Admin {
    
    public ModeratorAdmin() {
        super();
        setRole("MODERATOR");
    }

    public ModeratorAdmin(String username, String password, String fullName, String email) {
        super(username, password, "MODERATOR", fullName, email);
    }

    @Override
    public List<String> getPermissions() {
        List<String> permissions = super.getPermissions();
        permissions.add("MANAGE_ADMINS");
        return permissions;
    }

    @Override
    public String getAdminRole() {
        return "MODERATOR";
    }
}
