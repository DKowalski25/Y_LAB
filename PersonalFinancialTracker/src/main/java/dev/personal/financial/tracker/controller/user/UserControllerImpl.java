package dev.personal.financial.tracker.controller.user;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;
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
        return userService.getUserByEmail(email);
    }

    @Override
    public void deleteUser(String id) {
        userService.deleteUser(id);
    }
}