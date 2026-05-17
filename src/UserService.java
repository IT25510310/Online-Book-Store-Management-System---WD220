package com.example.onlinebookstore.service;

import com.example.onlinebookstore.model.RegularUser;
import com.example.onlinebookstore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<RegularUser> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<RegularUser> getUserById(String id) {
        return userRepository.findAll().stream()
                .filter(u -> u.getUserId() != null && u.getUserId().equalsIgnoreCase(id))
                .findFirst();
    }

    public List<RegularUser> searchUsers(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllUsers();
        }
        String lowerQuery = query.toLowerCase().trim();
        return userRepository.findAll().stream()
                .filter(u -> (u.getUserId() != null && u.getUserId().toLowerCase().contains(lowerQuery)) ||
                             (u.getUsername() != null && u.getUsername().toLowerCase().contains(lowerQuery)) ||
                             (u.getFullName() != null && u.getFullName().toLowerCase().contains(lowerQuery)))
                .collect(Collectors.toList());
    }

    public boolean register(RegularUser user) {
        // Handle update vs new registration
        Optional<RegularUser> existing = userRepository.findByUsername(user.getUsername());
        if (existing.isPresent() && user.getUserId() == null) {
            return false; // New registration with existing username
        }
        userRepository.save(user);
        return true;
    }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public Optional<RegularUser> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password));
    }

    public Optional<RegularUser> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
