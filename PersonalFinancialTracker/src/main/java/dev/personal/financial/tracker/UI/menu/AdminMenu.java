package dev.personal.financial.tracker.UI.menu;

import dev.personal.financial.tracker.UI.handler.AdminHandler;
import dev.personal.financial.tracker.util.ConsolePrinter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminMenu {
    private final AdminHandler adminHandler;
    private final ConsolePrinter printer;

    public void run() {
        while (true) {
            printer.printWithDivider("\nМеню администратора:");
            printer.printPrompt("1. Просмотреть всех пользователей");
            printer.printPrompt("2. Заблокировать пользователя");
            printer.printPrompt("3. Удалить пользователя");
            printer.printPrompt("4. Выйти из аккаунта");

            int choice = printer.readInt("Выберите номер действие:");

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
                    printer.printInfo("Выход из меню администратора");
                    return;
                default:
                    printer.printError("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }
}