package dev.personal.financial.tracker.UI;

import dev.personal.financial.tracker.UI.handler.*;
import dev.personal.financial.tracker.UI.menu.AdminMenu;
import dev.personal.financial.tracker.UI.menu.MainMenu;
import dev.personal.financial.tracker.UI.menu.UserMenu;
import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.controller.admin.AdminController;
import dev.personal.financial.tracker.controller.budget.BudgetController;
import dev.personal.financial.tracker.controller.goal.GoalController;
import dev.personal.financial.tracker.controller.transaction.TransactionController;
import dev.personal.financial.tracker.controller.user.UserController;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.UserRole;
import dev.personal.financial.tracker.repository.user.UserRepository;

import java.util.Scanner;

public class ConsoleInterface {
    private final MainMenu mainMenu;
    private final UserMenu userMenu;
    private final AdminMenu adminMenu;

    public ConsoleInterface(
            UserController userController,
            UserRepository userRepository,
            TransactionController transactionController,
            GoalController goalController,
            BudgetController budgetController,
            AdminController adminController
    ) {
        Scanner sc = new Scanner(System.in);
        ConsolePrinter printer = new ConsolePrinter(sc);
        UserHandler userHandler = new UserHandler(userController, userRepository, printer);
        TransactionHandler transactionHandler = new TransactionHandler(
                transactionController,
                budgetController,
                goalController,
                printer);
        GoalHandler goalHandler = new GoalHandler(goalController, printer);
        BudgetHandler budgetHandler = new BudgetHandler(budgetController, transactionController,printer);
        AdminHandler adminHandler = new AdminHandler(adminController, printer);

        this.mainMenu = new MainMenu(userHandler, transactionHandler, goalHandler,budgetHandler, adminHandler, printer);
        this.userMenu = new UserMenu(transactionHandler, goalHandler, budgetHandler, userHandler, printer);
        this.adminMenu = new AdminMenu(adminHandler, printer);
    }

    public void run() {
        while (true) {
            UserOut userOut = mainMenu.run();
            if (userOut != null) {
                if (userOut.getRole() == UserRole.ADMIN) {
                    adminMenu.run();
                } else {
                    userMenu.run(userOut);
                }
            }
        }
    }
}