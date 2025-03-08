package dev.personal.financial.tracker.service.admin;

import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;

import java.util.List;

public interface AdminService {
    List<UserOut> getAllUsers();
    void blockUser(String userId);
    void deleteUser(String userId);
}