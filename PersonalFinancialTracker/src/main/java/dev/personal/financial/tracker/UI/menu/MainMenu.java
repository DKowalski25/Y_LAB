package dev.personal.financial.tracker.UI.menu;

import dev.personal.financial.tracker.UI.handler.UserHandler;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;

import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class MainMenu {
    private final UserHandler userHandler;
    private final Scanner sc;

    public UserOut run() {
        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Регистрация");
            System.out.println("2. Вход");
            System.out.println("3. Выход");

            int choice = sc.nextInt();
//            sc.nextInt();

            switch (choice) {
                case 1:
                    return userHandler.registerUser();
                case 2:
                    return userHandler.loginUser();
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }
}