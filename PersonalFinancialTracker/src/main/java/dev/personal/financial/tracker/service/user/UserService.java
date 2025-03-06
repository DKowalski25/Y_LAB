package dev.personal.financial.tracker.service.user;

import dev.personal.financial.tracker.model.User;

public interface UserService {
    void registerUser(User user);
    User getUserById(String id);
    User getUserByEmail(String email);
    void deleteUser(String id);
}