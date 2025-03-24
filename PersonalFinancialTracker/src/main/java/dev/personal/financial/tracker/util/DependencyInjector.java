package dev.personal.financial.tracker.util;

import dev.personal.financial.tracker.controller.admin.AdminController;
import dev.personal.financial.tracker.controller.admin.AdminControllerImpl;
import dev.personal.financial.tracker.controller.budget.BudgetController;
import dev.personal.financial.tracker.controller.budget.BudgetControllerImpl;
import dev.personal.financial.tracker.controller.goal.GoalController;
import dev.personal.financial.tracker.controller.goal.GoalControllerImpl;
import dev.personal.financial.tracker.controller.transaction.TransactionController;
import dev.personal.financial.tracker.controller.transaction.TransactionControllerImpl;
import dev.personal.financial.tracker.controller.transaction.servlet.handlers.TransactionRequestRouter;
import dev.personal.financial.tracker.controller.transaction.servlet.handlers.TransactionRetrievalHandler;
import dev.personal.financial.tracker.controller.user.UserController;
import dev.personal.financial.tracker.controller.user.UserControllerImpl;
import dev.personal.financial.tracker.controller.user.servlet.handlers.UserRequestRouter;
import dev.personal.financial.tracker.controller.user.servlet.handlers.UserRetrievalHandler;
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

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import lombok.RequiredArgsConstructor;

import java.sql.Connection;

@RequiredArgsConstructor
public class DependencyInjector {

    private final Connection connection;

    public Validator createValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }

    public UserRepository createUserRepository() {
        return new UserRepositoryImpl(connection);
    }

    public UserService createUserService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    public UserController createUserController(UserService userService, ConsolePrinter consolePrinter) {
        return new UserControllerImpl(userService, consolePrinter);
    }

    public UserRetrievalHandler createUserRetrievalHandler(UserService userService) {
        return new UserRetrievalHandler(userService);
    }

    public UserRequestRouter createUserRequestRouter(UserRetrievalHandler retrievalHandler) {
        return new UserRequestRouter(retrievalHandler);
    }

    public TransactionRepository createTransactionRepository() {
        return new TransactionRepositoryImpl(connection);
    }

    public TransactionService createTransactionService(TransactionRepository transactionRepository) {
        return new TransactionServiceImpl(transactionRepository);
    }

    public TransactionController createTransactionController(TransactionService transactionService, ConsolePrinter consolePrinter) {
        return new TransactionControllerImpl(transactionService, consolePrinter);
    }

    public TransactionRetrievalHandler createTransactionRetrievalHandler(TransactionService transactionService) {
        return new TransactionRetrievalHandler(transactionService);
    }

    public TransactionRequestRouter createTransactionRequestRouter(TransactionRetrievalHandler retrievalHandler) {
        return new TransactionRequestRouter(retrievalHandler);
    }

    public GoalRepository createGoalRepository() {
        return new GoalRepositoryImpl(connection);
    }

    public GoalService createGoalService(GoalRepository goalRepository) {
        return new GoalServiceImpl(goalRepository);
    }

    public GoalController createGoalController(GoalService goalService, ConsolePrinter consolePrinter) {
        return new GoalControllerImpl(goalService, consolePrinter);
    }

    public BudgetRepository createBudgetRepository() {
        return new BudgetRepositoryImpl(connection);
    }

    public BudgetService createBudgetService(BudgetRepository budgetRepository) {
        return new BudgetServiceImpl(budgetRepository);
    }

    public BudgetController createBudgetController(BudgetService budgetService, ConsolePrinter consolePrinter) {
        return new BudgetControllerImpl(budgetService, consolePrinter);
    }

    public BudgetRetrievalHandler createBudgetRetrievalHandler(BudgetService budgetService) {
        return new BudgetRetrievalHandler(budgetService);
    }

    public BudgetRequestRouter createBudgetRequestRouter(BudgetRetrievalHandler retrievalHandler) {
        return new BudgetRequestRouter(retrievalHandler);
    }

    public AdminRepository createAdminRepository() {
        return new AdminRepositoryImpl(connection);
    }

    public AdminService createAdminService(AdminRepository adminRepository) {
        return new AdminServiceImpl(adminRepository);
    }

    public AdminController createAdminController(AdminService adminService, ConsolePrinter consolePrinter) {
        return new AdminControllerImpl(adminService, consolePrinter);
    }
}