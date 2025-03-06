package dev.personal.financial.tracker.utils;

import dev.personal.financial.tracker.controller.user.UserController;
import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.model.User;

import java.util.Scanner;


public class ConsoleInterface {
    private UserController userController;
    private Scanner sc;

    public ConsoleInterface(UserController userController) {
        this.userController = userController;
        this.sc = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Регистрация");
            System.out.println("2. Вход");
            System.out.println("3. Выход");

            int choice = sc.nextInt();
//            sc.nextInt();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }

    private void registerUser() {
        System.out.println("Введите имя пользователя:");
        String name = sc.next();

        System.out.println("Введите email:");
        String email = sc.next();

        System.out.println("Введите пароль:");
        String password = (String) sc.next();

        User user = new User(
                "1",
                name,
                email,
                password,
                "USER"
        );
        userController.registerUser(user);
        System.out.println("Пользователь успешно зарегистрирован.");
    }

    private void loginUser() {
        // Переделать реализацию
        System.out.println("Введите email:");
        String email = sc.next();

        System.out.println("Введите пароль:");
        String password = sc.next();

        User user = userController.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Пользователь успешно авторизован.");
        } else if (user == null){
            System.out.println("Нет такого юзера");
        } else {
            System.out.println("Неверный данные");
        }
    }
}
