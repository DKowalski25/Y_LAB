package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.controller.user.UserController;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.model.UserRole;

import lombok.RequiredArgsConstructor;

import java.util.Scanner;
import java.util.UUID;

@RequiredArgsConstructor
public class UserHandler {
    private final UserController userController;
    private final Scanner sc;

    public User registerUser() {
        System.out.println("Введите имя пользователя:");
        String name = sc.next();

        System.out.println("Введите email:");
        String email = sc.next();

        System.out.println("Введите пароль:");
        String password = sc.next();

        String id = UUID.randomUUID().toString();

        User user = new User(
                id,
                name,
                email,
                password,
                UserRole.USER
        );
        userController.registerUser(user);
        System.out.println("Пользователь успешно зарегистрирован.");
        return user;
    }

    public User loginUser() {
        // Переделать реализацию
        System.out.println("Введите email:");
        String email = sc.next();

        System.out.println("Введите пароль:");
        String password = sc.next();

        User user = userController.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Пользователь успешно авторизован.");
            return user;
        } else if (user == null){
            System.out.println("Пользователь с таким email не найден.");
        } else {
            System.out.println("Неверный пароль.");
        }
        return null;
    }
}