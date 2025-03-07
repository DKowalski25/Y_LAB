package dev.personal.financial.tracker.UI;

import dev.personal.financial.tracker.UI.handler.BudgetHandler;
import dev.personal.financial.tracker.UI.handler.GoalHandler;
import dev.personal.financial.tracker.UI.handler.TransactionHandler;
import dev.personal.financial.tracker.UI.handler.UserHandler;
import dev.personal.financial.tracker.UI.menu.MainMenu;
import dev.personal.financial.tracker.UI.menu.UserMenu;
import dev.personal.financial.tracker.controller.budget.BudgetController;
import dev.personal.financial.tracker.controller.goal.GoalController;
import dev.personal.financial.tracker.controller.transaction.TransactionController;
import dev.personal.financial.tracker.controller.user.UserController;
import dev.personal.financial.tracker.model.User;

import java.util.Scanner;

public class ConsoleInterface {
    private final MainMenu mainMenu;
    private final UserMenu userMenu;

    public ConsoleInterface(
            UserController userController,
            TransactionController transactionController,
            GoalController goalController,
            BudgetController budgetController
    ) {
        Scanner sc = new Scanner(System.in);
        UserHandler userHandler = new UserHandler(userController, sc);
        TransactionHandler transactionHandler = new TransactionHandler(transactionController, sc);
        GoalHandler goalHandler = new GoalHandler(goalController, sc);
        BudgetHandler budgetHandler = new BudgetHandler(budgetController, sc);

        this.mainMenu = new MainMenu(userHandler, sc);
        this.userMenu = new UserMenu(transactionHandler, goalHandler, budgetHandler, sc);
    }

    public void run() {
        while (true) {
            User user = mainMenu.run();
            if (user != null) {
                userMenu.run(user);
            }
        }
    }
}