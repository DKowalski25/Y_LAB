package dev.personal.financial.tracker.UI.menu;

import dev.personal.financial.tracker.UI.handler.AdminHandler;
import dev.personal.financial.tracker.UI.handler.UserHandler;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;

import dev.personal.financial.tracker.model.UserRole;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class MainMenu {
    private final UserHandler userHandler;
    private final AdminHandler adminHandler;
    private final Scanner sc;
    private final AdminMenu adminMenu;


    public MainMenu(UserHandler userHandler, AdminHandler adminHandler,Scanner sc) {
        this.userHandler = userHandler;
        this.adminHandler = adminHandler;
        this.sc = sc;
        this.adminMenu = new AdminMenu(adminHandler, sc);
    }

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
                    UserOut userOut = userHandler.loginUser();
                    if (userOut != null) {
                        if (userOut.getRole() == UserRole.ADMIN) {
                            adminMenu.run();
                            return null;
                        } else {
                            return userOut;
                        }
                    }
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }
}