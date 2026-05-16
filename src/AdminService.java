package com.example.onlinebookstore.service;

import com.example.onlinebookstore.model.ActivityLog;
import com.example.onlinebookstore.model.Admin;
import com.example.onlinebookstore.repository.ActivityLogRepository;
import com.example.onlinebookstore.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final ActivityLogRepository activityLogRepository;

    public AdminService(AdminRepository adminRepository, ActivityLogRepository activityLogRepository) {
        this.adminRepository = adminRepository;
        this.activityLogRepository = activityLogRepository;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public void saveAdmin(Admin admin, String oldUsername) {
        adminRepository.save(admin, oldUsername);
    }

    public void deleteAdmin(String username) {
        adminRepository.deleteByUsername(username);
    }

    public Optional<Admin> authenticate(String username, String password) {
        return adminRepository.findAll().stream()
                .filter(a -> a.getUsername().equalsIgnoreCase(username) && a.getPassword().equals(password))
                .findFirst();
    }

    public int getRoleLevel(String role) {
        if (role == null) return 0;
        switch (role.toUpperCase()) {
            case "SUPER_ADMIN": return 3;
            case "MODERATOR": return 2;
            case "VIEWER": return 1;
            default: return 0;
        }
    }

    public boolean canManage(String actorRole, String targetRole) {
        return getRoleLevel(actorRole) >= getRoleLevel(targetRole);
    }

    public boolean hasPermission(String username, String permission) {
        return getAdminByUsername(username)
                .map(admin -> admin.getPermissions().contains(permission))
                .orElse(false);
    }

    public List<ActivityLog> getAllActivityLogs() {
        List<ActivityLog> logs = activityLogRepository.findAll();
        Collections.reverse(logs); // Show newest logs first
        return logs;
    }

    public void logActivity(String username, String action) {
        activityLogRepository.save(new ActivityLog(username, action));
    }
}
