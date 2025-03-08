package dev.personal.financial.tracker.UI.menu;

import dev.personal.financial.tracker.UI.handler.AdminHandler;
import dev.personal.financial.tracker.UI.handler.UserHandler;
import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.UserRole;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MainMenu {
    private final UserHandler userHandler;
    private final AdminHandler adminHandler;
    private final ConsolePrinter printer;
    private final AdminMenu adminMenu;

    public MainMenu(UserHandler userHandler, AdminHandler adminHandler, ConsolePrinter printer) {
        this.userHandler = userHandler;
        this.adminHandler = adminHandler;
        this.printer = printer;
        this.adminMenu = new AdminMenu(adminHandler, printer);
    }

    public UserOut run() {
        while (true) {
            printer.printWithDivider("\nВыберите действие:");
            printer.printPrompt("1. Регистрация");
            printer.printPrompt("2. Вход");
            printer.printPrompt("3. Выход");

            int choice = printer.readInt("Выберите номер действие:");

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
                    printer.printInfo("Программа завершена.");
                    System.exit(0);
                    break;
                default:
                    printer.printError("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }
}