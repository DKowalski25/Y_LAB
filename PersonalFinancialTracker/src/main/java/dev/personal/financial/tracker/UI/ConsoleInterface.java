package dev.personal.financial.tracker.UI;

import dev.personal.financial.tracker.UI.handler.*;
import dev.personal.financial.tracker.UI.menu.AdminMenu;
import dev.personal.financial.tracker.UI.menu.MainMenu;
import dev.personal.financial.tracker.UI.menu.UserMenu;
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
        UserHandler userHandler = new UserHandler(userController, sc, userRepository);
        TransactionHandler transactionHandler = new TransactionHandler(transactionController, sc);
        GoalHandler goalHandler = new GoalHandler(goalController, sc);
        BudgetHandler budgetHandler = new BudgetHandler(budgetController, sc);
        AdminHandler adminHandler = new AdminHandler(adminController, sc);

        this.mainMenu = new MainMenu(userHandler, adminHandler, sc);
        this.userMenu = new UserMenu(transactionHandler, goalHandler, budgetHandler, sc);
        this.adminMenu = new AdminMenu(adminHandler, sc);
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