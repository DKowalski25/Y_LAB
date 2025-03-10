package dev.personal.financial.tracker.controller.admin;

import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.exception.user.UserAlreadyBlockedException;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.service.admin.AdminService;

import dev.personal.financial.tracker.util.ConsolePrinter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Реализация интерфейса {@link AdminController}.
 */
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {
    private final AdminService adminService;
    private final ConsolePrinter printer;

    @Override
    public List<UserOut> getAllUsers() {
        return adminService.getAllUsers();
    }

    @Override
    public void blockUser(int userId) {
        try {
            adminService.blockUser(userId);
        } catch (UserNotFoundException | UserAlreadyBlockedException e) {
            printer.printError(e.getMessage());
        }
    }

    @Override
    public void deleteUser(int userId) {
        try {
            adminService.deleteUser(userId);
        } catch (UserNotFoundException e) {
            printer.printError(e.getMessage());
        }
    }
}