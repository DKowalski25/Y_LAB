package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.controller.user.UserController;
import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserMapper;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.model.UserRole;

import dev.personal.financial.tracker.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;
import java.util.UUID;

@RequiredArgsConstructor
public class UserHandler {
    private final UserController userController;
    private final Scanner sc;
    private final UserRepository userRepository;

    public UserOut registerUser() {
        System.out.println("Введите имя пользователя:");
        String name = sc.next();

        System.out.println("Введите email:");
        String email = sc.next();

        System.out.println("Введите пароль:");
        String password = sc.next();

        String id = UUID.randomUUID().toString();

        UserIn user = new UserIn(
                id,
                name,
                email,
                password,
                UserRole.USER
        );
        userController.registerUser(user);
        System.out.println("Пользователь успешно зарегистрирован.");
        return userController.getUserByEmail(email);
    }

    public UserOut loginUser() {
        // Переделать реализацию
        System.out.println("Введите email:");
        String email = sc.next();

        System.out.println("Введите пароль:");
        String password = sc.next();

        User user = userRepository.getByEmail(email);
        UserOut userOut = userController.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            if (user.getIsBlocked()) {
                System.out.println("Пользователь заблокирован.");
            } else {
                System.out.println("Пользователь успешно авторизован.");
                return userOut;
            }
        } else if (user == null){
            System.out.println("Пользователь с таким email не найден.");
        } else {
            System.out.println("Неверный пароль.");
        }
        return null;
    }
}