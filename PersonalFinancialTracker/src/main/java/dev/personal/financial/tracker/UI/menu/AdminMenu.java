package dev.personal.financial.tracker.UI.menu;

import dev.personal.financial.tracker.UI.handler.AdminHandler;

import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class AdminMenu {
    private final AdminHandler adminHandler;
    private final Scanner sc;

    public void run() {
        while (true) {
            System.out.println("\nМеню администратора:");
            System.out.println("1. Просмотреть всех пользователей");
            System.out.println("2. Заблокировать пользователя");
            System.out.println("3. Удалить пользователя");
            System.out.println("4. Выйти из аккаунта");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    adminHandler.viewUsers();
                    break;
                case 2:
                    adminHandler.blockUser();
                    break;
                case 3:
                    adminHandler.deleteUser();
                    break;
                case 4:
                    System.out.println("Выход из меню администратора");
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }



    }

}