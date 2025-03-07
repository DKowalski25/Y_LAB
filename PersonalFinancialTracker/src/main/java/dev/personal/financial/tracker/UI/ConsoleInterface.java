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
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.repository.user.UserRepository;

import java.util.Scanner;

public class ConsoleInterface {
    private final MainMenu mainMenu;
    private final UserMenu userMenu;

    public ConsoleInterface(
            UserController userController,
            UserRepository userRepository,
            TransactionController transactionController,
            GoalController goalController,
            BudgetController budgetController
    ) {
        Scanner sc = new Scanner(System.in);
        UserHandler userHandler = new UserHandler(userController, sc, userRepository);
        TransactionHandler transactionHandler = new TransactionHandler(transactionController, sc);
        GoalHandler goalHandler = new GoalHandler(goalController, sc);
        BudgetHandler budgetHandler = new BudgetHandler(budgetController, sc);

        this.mainMenu = new MainMenu(userHandler, sc);
        this.userMenu = new UserMenu(transactionHandler, goalHandler, budgetHandler, sc);
    }

    public void run() {
        while (true) {
            UserOut userOut = mainMenu.run();
            if (userOut != null) {
                userMenu.run(userOut);
            }
        }
    }
}