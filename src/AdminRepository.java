package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.Admin;
import com.example.onlinebookstore.model.ModeratorAdmin;
import com.example.onlinebookstore.model.SuperAdmin;
import com.example.onlinebookstore.model.ViewerAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepository {

    private final String filePath;

    public AdminRepository(@Value("${admin.file.path}") String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    private void ensureFileExists() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                // Initialize with default super admin
                save(new SuperAdmin("admin", "admin123", "System Administrator", "admin@example.com"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String role = parts[2];
                    String fullName = parts.length > 3 ? parts[3] : "N/A";
                    String email = parts.length > 4 ? parts[4] : "N/A";
                    
                    Admin admin;
                    if ("SUPER_ADMIN".equalsIgnoreCase(role) || "ADMIN".equalsIgnoreCase(role)) {
                        admin = new SuperAdmin(username, password, fullName, email);
                    } else if ("MODERATOR".equalsIgnoreCase(role) || "EDITOR".equalsIgnoreCase(role)) {
                        admin = new ModeratorAdmin(username, password, fullName, email);
                    } else {
                        admin = new ViewerAdmin(username, password, fullName, email);
                    }
                    admins.add(admin);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return admins;
    }

    public Optional<Admin> findByUsername(String username) {
        return findAll().stream()
                .filter(a -> a.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public void save(Admin admin, String oldUsername) {
        List<Admin> admins = findAll();
        boolean found = false;
        
        // If oldUsername is provided, look for that specific record to update
        String targetUsername = (oldUsername != null && !oldUsername.isEmpty()) ? oldUsername : admin.getUsername();
        
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i).getUsername().equalsIgnoreCase(targetUsername)) {
                admins.set(i, admin);
                found = true;
                break;
            }
        }
        
        if (!found) {
            admins.add(admin);
        }
        writeAll(admins);
    }

    public void save(Admin admin) {
        save(admin, null);
    }

    public void deleteByUsername(String username) {
        List<Admin> admins = findAll();
        admins.removeIf(a -> a.getUsername().equalsIgnoreCase(username));
        writeAll(admins);
    }

    private void writeAll(List<Admin> admins) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Admin admin : admins) {
                writer.write(String.format("%s,%s,%s,%s,%s",
                        admin.getUsername(),
                        admin.getPassword(),
                        admin.getRole(),
                        admin.getFullName(),
                        admin.getEmail()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
