package dev.personal.financial.tracker;

import dev.personal.financial.tracker.controller.budget.BudgetController;
import dev.personal.financial.tracker.controller.budget.BudgetControllerImpl;
import dev.personal.financial.tracker.controller.goal.GoalController;
import dev.personal.financial.tracker.controller.goal.GoalControllerImpl;
import dev.personal.financial.tracker.controller.transaction.TransactionController;
import dev.personal.financial.tracker.controller.transaction.TransactionControllerImpl;
import dev.personal.financial.tracker.controller.user.UserController;
import dev.personal.financial.tracker.controller.user.UserControllerImpl;
import dev.personal.financial.tracker.repository.budget.BudgetRepository;
import dev.personal.financial.tracker.repository.budget.BudgetRepositoryImpl;
import dev.personal.financial.tracker.repository.goal.GoalRepository;
import dev.personal.financial.tracker.repository.goal.GoalRepositoryImpl;
import dev.personal.financial.tracker.repository.transaction.TransactionRepository;
import dev.personal.financial.tracker.repository.transaction.TransactionRepositoryImpl;
import dev.personal.financial.tracker.repository.user.UserRepository;
import dev.personal.financial.tracker.repository.user.UserRepositoryImpl;
import dev.personal.financial.tracker.service.budget.BudgetService;
import dev.personal.financial.tracker.service.budget.BudgetServiceImpl;
import dev.personal.financial.tracker.service.goal.GoalService;
import dev.personal.financial.tracker.service.goal.GoalServiceImpl;
import dev.personal.financial.tracker.service.transaction.TransactionService;
import dev.personal.financial.tracker.service.transaction.TransactionServiceImpl;
import dev.personal.financial.tracker.service.user.UserService;
import dev.personal.financial.tracker.service.user.UserServiceImpl;
import dev.personal.financial.tracker.UI.ConsoleInterface;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        UserController userController = new UserControllerImpl(userService);

        TransactionRepository transactionRepository = new TransactionRepositoryImpl();
        TransactionService transactionService = new TransactionServiceImpl(transactionRepository);
        TransactionController transactionController = new TransactionControllerImpl(transactionService);

        GoalRepository goalRepository = new GoalRepositoryImpl();
        GoalService goalService = new GoalServiceImpl(goalRepository);
        GoalController goalController = new GoalControllerImpl(goalService);

        BudgetRepository budgetRepository = new BudgetRepositoryImpl();
        BudgetService budgetService = new BudgetServiceImpl(budgetRepository);
        BudgetController budgetController = new BudgetControllerImpl(budgetService);

        ConsoleInterface consoleInterface = new ConsoleInterface(
                userController,
                userRepository,
                transactionController,
                goalController,
                budgetController

        );
        consoleInterface.run();
    }
}