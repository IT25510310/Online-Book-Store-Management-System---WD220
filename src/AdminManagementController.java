package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.model.Admin;
import com.example.onlinebookstore.model.ViewerAdmin;
import com.example.onlinebookstore.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminManagementController {

    private final AdminService adminService;

    public AdminManagementController(AdminService adminService) {
        this.adminService = adminService;
    }

    @ModelAttribute("admin")
    public Admin admin() {
        return new ViewerAdmin();
    }

    @GetMapping("/admins")
    public String viewAdmins(HttpSession session, Model model) {
        String currentUserRole = (String) session.getAttribute("userRole");
        if (currentUserRole == null || adminService.getRoleLevel(currentUserRole) < 1) {
            return "redirect:/login-choice";
        }
        
        model.addAttribute("admins", adminService.getAllAdmins());
        model.addAttribute("currentRoleLevel", adminService.getRoleLevel(currentUserRole));
        
        // Pass role levels for UI logic
        Map<String, Integer> roleLevels = new HashMap<>();
        adminService.getAllAdmins().forEach(a -> roleLevels.put(a.getUsername(), adminService.getRoleLevel(a.getRole())));
        model.addAttribute("roleLevels", roleLevels);
        
        return "admin-list";
    }

    @GetMapping("/admins/new")
    public String showAddAdminForm(HttpSession session, Model model) {
        if (!"SUPER_ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/admin/admins?error=permission-denied";
        }
        model.addAttribute("admin", new ViewerAdmin());
        model.addAttribute("isEdit", false);
        return "admin-form";
    }

    @GetMapping("/admins/edit/{username}")
    public String showEditAdminForm(@PathVariable String username, HttpSession session, Model model) {
        String currentUserRole = (String) session.getAttribute("userRole");
        if (currentUserRole == null || adminService.getRoleLevel(currentUserRole) < 2) {
            return "redirect:/admin/admins?error=permission-denied";
        }
        
        Admin targetAdmin = adminService.getAdminByUsername(username).orElse(null);
        if (targetAdmin == null) {
            return "redirect:/admin/admins?error=not-found";
        }
        
        // Only higher level can edit lower level (except self)
        String currentUsername = (String) session.getAttribute("username");
        if (!username.equalsIgnoreCase(currentUsername) && adminService.getRoleLevel(currentUserRole) < adminService.getRoleLevel(targetAdmin.getRole())) {
            return "redirect:/admin/admins?error=permission-denied";
        }

        model.addAttribute("admin", targetAdmin);
        model.addAttribute("isEdit", true);
        model.addAttribute("targetRoleLevel", adminService.getRoleLevel(targetAdmin.getRole()));
        model.addAttribute("currentRoleLevel", adminService.getRoleLevel(currentUserRole));
        
        return "admin-form";
    }

    @PostMapping("/admins/save")
    public String saveAdmin(@ModelAttribute Admin admin, @RequestParam(required = false) String oldUsername, HttpSession session) {
        String currentUserRole = (String) session.getAttribute("userRole");
        String currentUsername = (String) session.getAttribute("username");
        
        if (currentUserRole == null || adminService.getRoleLevel(currentUserRole) < 2) {
            return "redirect:/admin/admins?error=permission-denied";
        }

        // If it's a new admin, we force a role
        if (admin.getRole() == null || admin.getRole().isEmpty()) {
            admin.setRole("SUPER_ADMIN");
        }
        
        // Business Rule: Cannot elevate someone to a higher role than yours
        if (adminService.getRoleLevel(admin.getRole()) > adminService.getRoleLevel(currentUserRole)) {
            return "redirect:/admin/admins?error=role-elevation-denied";
        }

        boolean isNew = (oldUsername == null || oldUsername.isEmpty());
        adminService.saveAdmin(admin, oldUsername);
        
        // If current user updated their own profile (including username change)
        if (!isNew && oldUsername.equalsIgnoreCase(currentUsername)) {
            session.setAttribute("username", admin.getUsername());
            session.setAttribute("userRole", admin.getRole());
        }
        
        String action = isNew ? "Created new admin account: " : "Updated admin account details: ";
        adminService.logActivity(currentUsername, action + admin.getUsername());
        
        return "redirect:/admin/admins";
    }

    @GetMapping("/admins/delete/{username}")
    public String deleteAdmin(@PathVariable String username, HttpSession session) {
        String currentUser = (String) session.getAttribute("username");
        String currentUserRole = (String) session.getAttribute("userRole");

        if (currentUserRole == null || adminService.getRoleLevel(currentUserRole) < 1) {
            return "redirect:/login-choice";
        }
        
        Admin targetAdmin = adminService.getAdminByUsername(username).orElse(null);
        if (targetAdmin == null) {
            return "redirect:/admin/admins?error=not-found";
        }

        // Hierarchy Enforcement: Must have >= level to delete
        if (!adminService.canManage(currentUserRole, targetAdmin.getRole())) {
            return "redirect:/admin/admins?error=permission-denied";
        }
        
        // Safety check: Prevent deleting the last admin
        if (adminService.getAllAdmins().size() <= 1) {
            return "redirect:/admin/admins?error=last-admin";
        }
        
        adminService.deleteAdmin(username);
        adminService.logActivity(currentUser, "Deleted admin account: " + username);

        // Handle self-deletion
        if (username.equalsIgnoreCase(currentUser)) {
            session.invalidate();
            return "redirect:/login-choice?msg=account-deleted";
        }
        
        return "redirect:/admin/admins";
    }

    @GetMapping("/logs")
    public String viewLogs(HttpSession session, Model model) {
        String currentUserRole = (String) session.getAttribute("userRole");
        if (currentUserRole == null || adminService.getRoleLevel(currentUserRole) < 1) {
            return "redirect:/login-choice";
        }
        model.addAttribute("logs", adminService.getAllActivityLogs());
        return "admin-logs";
    }
}
