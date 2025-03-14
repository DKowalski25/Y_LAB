package dev.personal.financial.tracker;

import dev.personal.financial.tracker.UI.ConsoleInterface;
import dev.personal.financial.tracker.controller.admin.AdminController;
import dev.personal.financial.tracker.controller.budget.BudgetController;
import dev.personal.financial.tracker.controller.goal.GoalController;
import dev.personal.financial.tracker.controller.transaction.TransactionController;
import dev.personal.financial.tracker.controller.user.UserController;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.model.UserRole;
import dev.personal.financial.tracker.repository.admin.AdminRepository;
import dev.personal.financial.tracker.repository.budget.BudgetRepository;
import dev.personal.financial.tracker.repository.goal.GoalRepository;
import dev.personal.financial.tracker.repository.transaction.TransactionRepository;
import dev.personal.financial.tracker.repository.user.UserRepository;
import dev.personal.financial.tracker.service.admin.AdminService;
import dev.personal.financial.tracker.service.budget.BudgetService;
import dev.personal.financial.tracker.service.goal.GoalService;
import dev.personal.financial.tracker.service.transaction.TransactionService;
import dev.personal.financial.tracker.service.user.UserService;
import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.util.DependencyInjector;

import java.util.Scanner;

public class Application {

    private static final Application INSTANCE = new Application();

    private Application() {}

    public static Application getInstance() {
        return INSTANCE;
    }

    public void run() {
        DependencyInjector injector = new DependencyInjector();

        UserRepository userRepository = injector.createUserRepository();
        UserService userService = injector.createUserService(userRepository);
        UserController userController = injector.createUserController(userService, new ConsolePrinter(new Scanner(System.in)));

        TransactionRepository transactionRepository = injector.createTransactionRepository();
        TransactionService transactionService = injector.createTransactionService(transactionRepository);
        TransactionController transactionController = injector.createTransactionController(transactionService, new ConsolePrinter(new Scanner(System.in)));

        GoalRepository goalRepository = injector.createGoalRepository();
        GoalService goalService = injector.createGoalService(goalRepository);
        GoalController goalController = injector.createGoalController(goalService, new ConsolePrinter(new Scanner(System.in)));

        BudgetRepository budgetRepository = injector.createBudgetRepository();
        BudgetService budgetService = injector.createBudgetService(budgetRepository);
        BudgetController budgetController = injector.createBudgetController(budgetService, new ConsolePrinter(new Scanner(System.in)));

        AdminRepository adminRepository = injector.createAdminRepository(userRepository);
        AdminService adminService = injector.createAdminService(adminRepository);
        AdminController adminController = injector.createAdminController(adminService, new ConsolePrinter(new Scanner(System.in)));

        ConsoleInterface consoleInterface = new ConsoleInterface(
                userController,
                userRepository,
                transactionController,
                goalController,
                budgetController,
                adminController
        );

        User admin = new User(
                9999,
                "Admin",
                "a@admin.com",
                "123456",
                UserRole.ADMIN,
                false
        );

        userRepository.save(admin);
        consoleInterface.run();
    }
}