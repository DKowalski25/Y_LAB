package dev.personal.financial.tracker.controller.user;

import dev.personal.financial.tracker.model.User;

public interface UserController {
    void registerUser(User user);
    User getUserById(String id);
    User getUserByEmail(String email);
    void deleteUser(String id);
}