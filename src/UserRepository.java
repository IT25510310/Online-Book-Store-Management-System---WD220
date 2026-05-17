package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.RegularUser;
import com.example.onlinebookstore.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final String filePath;

    public UserRepository(@Value("${user.file.path}") String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    private void ensureFileExists() {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            try {
                file.getParentFile().mkdirs();
                if (!file.exists()) file.createNewFile();
                // Initialize with a default user for testing
                save(new RegularUser("U1001", "user", "user123", "user@example.com", "Test User", "000-000-0000", "Default Address"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<RegularUser> findAll() {
        List<RegularUser> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String delimiter = line.contains("|") ? "\\|" : ",";
                String[] parts = line.split(delimiter);
                
                if (delimiter.equals("\\|") && parts.length >= 7) {
                    // New Format: id|username|password|email|fullName|contact|address
                    users.add(new RegularUser(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
                } else if (parts.length >= 4) {
                    // Old Format Migration: username,password,email,fullName,...
                    String username = parts[0];
                    String password = parts[1];
                    String email = parts[2];
                    String fullName = parts[3];
                    String contact = parts.length > 4 ? parts[4] : "N/A";
                    String address = parts.length > 5 ? parts[5] : "N/A";
                    
                    users.add(new RegularUser(null, username, password, email, fullName, contact, address));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Finalize migration by assigning IDs
        boolean needsRewrite = false;
        for (RegularUser u : users) {
            if (u.getUserId() == null) {
                u.setUserId(generateNextId(users));
                needsRewrite = true;
            }
        }
        if (needsRewrite) writeAll(users);
        
        return users;
    }

    public String generateNextId(List<RegularUser> currentUsers) {
        int maxId = 1000;
        for (RegularUser u : currentUsers) {
            if (u.getUserId() != null && u.getUserId().startsWith("U")) {
                try {
                    int idVal = Integer.parseInt(u.getUserId().substring(1));
                    if (idVal > maxId) maxId = idVal;
                } catch (NumberFormatException ignored) {}
            }
        }
        return "U" + (maxId + 1);
    }

    public Optional<RegularUser> findByUsername(String username) {
        return findAll().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public void save(RegularUser user) {
        List<RegularUser> users = findAll();
        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            user.setUserId(generateNextId(users));
        }
        users.removeIf(u -> u.getUsername().equalsIgnoreCase(user.getUsername()));
        users.add(user);
        writeAll(users);
    }

    public void deleteByUsername(String username) {
        List<RegularUser> users = findAll();
        users.removeIf(u -> u.getUsername().equalsIgnoreCase(username));
        writeAll(users);
    }

    private void writeAll(List<RegularUser> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (RegularUser user : users) {
                writer.write(String.format("%s|%s|%s|%s|%s|%s|%s",
                        user.getUserId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getFullName(),
                        user.getContactNumber(),
                        user.getAddress()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
