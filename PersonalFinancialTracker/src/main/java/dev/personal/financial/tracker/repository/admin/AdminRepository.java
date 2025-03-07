package dev.personal.financial.tracker.repository.admin;

import dev.personal.financial.tracker.model.User;

import java.util.List;

public interface AdminRepository {
    List<User> getAllUsers();
    void blockUser(String userId);
    void deleteUser(String userId);
}