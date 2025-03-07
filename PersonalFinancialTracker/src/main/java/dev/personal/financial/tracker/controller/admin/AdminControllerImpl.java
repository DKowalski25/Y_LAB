package dev.personal.financial.tracker.controller.admin;

import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.service.admin.AdminService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {
    private final AdminService adminService;

    @Override
    public List<UserOut> getAllUsers() {
        return adminService.getAllUsers();
    }

    @Override
    public void blockUser(String userId) {
        adminService.blockUser(userId);
    }

    @Override
    public void deleteUser(String userId) {
        adminService.deleteUser(userId);
    }
}