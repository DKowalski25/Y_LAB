package dev.personal.financial.tracker.controller.admin;

import dev.personal.financial.tracker.dto.user.UserOut;

import java.util.List;

public interface AdminController {
    List<UserOut> getAllUsers();
    void blockUser(String userId);
    void deleteUser(String userId);
}