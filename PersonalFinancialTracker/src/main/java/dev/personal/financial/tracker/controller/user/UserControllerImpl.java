package dev.personal.financial.tracker.controller.user;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.service.user.UserService;
import dev.personal.financial.tracker.util.ConsolePrinter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final ConsolePrinter printer;

    @Override
    public void registerUser(UserIn userIn) {
        try {
            userService.registerUser(userIn);
        } catch (IllegalArgumentException e) {
            printer.printError(e.getMessage());
        }

    }

    @Override
    public UserOut getUserById(String id) {
        return userService.getUserById(id);
    }

    @Override
    public UserOut getUserByEmail(String email) {
        try {
            return userService.getUserByEmail(email);
        } catch (IllegalArgumentException e) {
            printer.printError(e.getMessage());
        } return null;
    }

    @Override
    public void updateUser(String email, UserIn userIn) {
        try {
            userService.updateUser(email, userIn);
            printer.printSuccess("Профиль успешно обновлен.");
        } catch (IllegalArgumentException e) {
            printer.printError(e.getMessage());
        }
    }

    @Override
    public void deleteUserByEmail(String email) {
        try {
            userService.deleteUserEmail(email);
            printer.printSuccess("Аккаунт успешно удален.");
        } catch (IllegalArgumentException e) {
            printer.printError(e.getMessage());
        }
    }
}