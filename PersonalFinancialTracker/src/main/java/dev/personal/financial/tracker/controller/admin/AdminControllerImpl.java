package dev.personal.financial.tracker.controller.admin;

import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.exception.user.UserAlreadyBlockedException;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.service.admin.AdminService;

import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Реализация интерфейса {@link AdminController}.
 */
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {
    private final AdminService adminService;

    @Override
    public List<UserOut> getAllUsers() {
        return adminService.getAllUsers();
    }

    @Override
    public void blockUser(String userId) {
        try {
            adminService.blockUser(userId);
        } catch (UserNotFoundException | UserAlreadyBlockedException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void deleteUser(String userId) {
        try {
            adminService.deleteUser(userId);
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}