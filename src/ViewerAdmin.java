package com.example.onlinebookstore.model;

public class ViewerAdmin extends Admin {
    
    public ViewerAdmin() {
        super();
        setRole("VIEWER");
    }

    public ViewerAdmin(String username, String password, String fullName, String email) {
        super(username, password, "VIEWER", fullName, email);
    }

    @Override
    public String getAdminRole() {
        return "VIEWER";
    }
}
