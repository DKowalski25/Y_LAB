package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.controller.user.UserController;
import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.model.UserRole;
import dev.personal.financial.tracker.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UserHandler {
    private final UserController userController;
    private final UserRepository userRepository;
    private final ConsolePrinter printer;

    public UserOut registerUser() {
        String name = printer.readNonEmptyString("Введите имя:");
        if (name == null) {
            printer.printInfo("Регистрация отменена.");
            return null;
        }
        String email = printer.readEmail("Введите email:");
        if (email == null) {
            printer.printInfo("Регистрация отменена.");
            return null;
        }
        String password = printer.readPassword("Введите пароль:");
        if (password == null) {
            printer.printInfo("Регистрация отменена.");
            return null;
        }

        String id = UUID.randomUUID().toString();

        UserIn user = new UserIn(
                id,
                name,
                email,
                password,
                UserRole.USER
        );
        userController.registerUser(user);
        printer.printSuccess("Пользователь успешно зарегистрирован.");
        return userController.getUserByEmail(email);
    }

    public UserOut loginUser() {
        String email = printer.readEmail("Введите email:");
        if (email == null) {
            printer.printInfo("Вход отменен.");
            return null;
        }

        String password = printer.readPassword("Введите пароль:");
        if (password == null) {
            printer.printInfo("Вход отменен.");
            return null;
        }

        User user = userRepository.getByEmail(email);
        UserOut userOut = userController.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            if (user.getIsBlocked()) {
                printer.printError("Пользователь заблокирован.");
            } else {
                printer.printSuccess("Пользователь успешно авторизован.");
                return userOut;
            }
        } else if (user == null){
            printer.printError("Пользователь с таким email не найден.");
        } else {
            printer.printError("Неверный пароль.");
        }
        return null;
    }

    public UserOut updateProfile(String email) {
        String name = printer.readNonEmptyString("Введите имя:");
        if (name == null) {
            printer.printInfo("Обновление отменено.");
            return null;
        }

        String newEmail = printer.readEmail("Введите email:");
        if (newEmail == null) {
            printer.printInfo("Обновление отменено.");
            return null;
        }

        String password = printer.readPassword("Введите пароль:");
        if (password == null) {
            printer.printInfo("Обновление отменено.");
            return null;
        }

        UserIn user = new UserIn(
                null,
                name,
                newEmail,
                password,
                UserRole.USER
        );
        userController.updateUser(email, user);
        return userController.getUserByEmail(newEmail);
    }

    public boolean deleteAccount(String email) {
        Boolean confirm = printer.readBoolean("Вы уверены, что хотите удалить аккаунт? ");
        if (confirm == null || !confirm) {
            printer.printInfo("Удаление аккаунта отменено.");
            return false;
        }

        userController.deleteUserByEmail(email);
        return true;
    }
}