package dev.personal.financial.tracker.util;

import dev.personal.financial.tracker.controller.admin.AdminController;
import dev.personal.financial.tracker.controller.admin.AdminControllerImpl;
import dev.personal.financial.tracker.controller.budget.BudgetController;
import dev.personal.financial.tracker.controller.budget.BudgetControllerImpl;
import dev.personal.financial.tracker.controller.goal.GoalController;
import dev.personal.financial.tracker.controller.goal.GoalControllerImpl;
import dev.personal.financial.tracker.controller.transaction.TransactionController;
import dev.personal.financial.tracker.controller.transaction.TransactionControllerImpl;
import dev.personal.financial.tracker.controller.user.UserController;
import dev.personal.financial.tracker.controller.user.UserControllerImpl;
import dev.personal.financial.tracker.repository.admin.AdminRepository;
import dev.personal.financial.tracker.repository.admin.AdminRepositoryImpl;
import dev.personal.financial.tracker.repository.budget.BudgetRepository;
import dev.personal.financial.tracker.repository.budget.BudgetRepositoryImpl;
import dev.personal.financial.tracker.repository.goal.GoalRepository;
import dev.personal.financial.tracker.repository.goal.GoalRepositoryImpl;
import dev.personal.financial.tracker.repository.transaction.TransactionRepository;
import dev.personal.financial.tracker.repository.transaction.TransactionRepositoryImpl;
import dev.personal.financial.tracker.repository.user.UserRepository;
import dev.personal.financial.tracker.repository.user.UserRepositoryImpl;
import dev.personal.financial.tracker.service.admin.AdminService;
import dev.personal.financial.tracker.service.admin.AdminServiceImpl;
import dev.personal.financial.tracker.service.budget.BudgetService;
import dev.personal.financial.tracker.service.budget.BudgetServiceImpl;
import dev.personal.financial.tracker.service.goal.GoalService;
import dev.personal.financial.tracker.service.goal.GoalServiceImpl;
import dev.personal.financial.tracker.service.transaction.TransactionService;
import dev.personal.financial.tracker.service.transaction.TransactionServiceImpl;
import dev.personal.financial.tracker.service.user.UserService;
import dev.personal.financial.tracker.service.user.UserServiceImpl;

public class DependencyInjector {

    public UserRepository createUserRepository() {
        return new UserRepositoryImpl();
    }

    public UserService createUserService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    public UserController createUserController(UserService userService, ConsolePrinter consolePrinter) {
        return new UserControllerImpl(userService, consolePrinter);
    }

    public TransactionRepository createTransactionRepository() {
        return new TransactionRepositoryImpl();
    }

    public TransactionService createTransactionService(TransactionRepository transactionRepository) {
        return new TransactionServiceImpl(transactionRepository);
    }

    public TransactionController createTransactionController(TransactionService transactionService, ConsolePrinter consolePrinter) {
        return new TransactionControllerImpl(transactionService, consolePrinter);
    }

    public GoalRepository createGoalRepository() {
        return new GoalRepositoryImpl();
    }

    public GoalService createGoalService(GoalRepository goalRepository) {
        return new GoalServiceImpl(goalRepository);
    }

    public GoalController createGoalController(GoalService goalService) {
        return new GoalControllerImpl(goalService);
    }

    public BudgetRepository createBudgetRepository() {
        return new BudgetRepositoryImpl();
    }

    public BudgetService createBudgetService(BudgetRepository budgetRepository) {
        return new BudgetServiceImpl(budgetRepository);
    }

    public BudgetController createBudgetController(BudgetService budgetService) {
        return new BudgetControllerImpl(budgetService);
    }

    public AdminRepository createAdminRepository(UserRepository userRepository) {
        return new AdminRepositoryImpl(userRepository);
    }

    public AdminService createAdminService(AdminRepository adminRepository) {
        return new AdminServiceImpl(adminRepository);
    }

    public AdminController createAdminController(AdminService adminService) {
        return new AdminControllerImpl(adminService);
    }
}