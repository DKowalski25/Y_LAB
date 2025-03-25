package dev.personal.financial.tracker.controller;

import dev.personal.financial.tracker.controller.admin.servlet.AdminServlet;
import dev.personal.financial.tracker.controller.admin.servlet.handlers.AdminRequestRouter;
import dev.personal.financial.tracker.controller.admin.servlet.handlers.AdminRetrievalHandler;
import dev.personal.financial.tracker.controller.budget.servlet.BudgetServlet;
import dev.personal.financial.tracker.controller.budget.servlet.handlers.BudgetRequestRouter;
import dev.personal.financial.tracker.controller.budget.servlet.handlers.BudgetRetrievalHandler;
import dev.personal.financial.tracker.controller.goal.servlet.GoalServlet;
import dev.personal.financial.tracker.controller.goal.servlet.handlers.GoalRequestRouter;
import dev.personal.financial.tracker.controller.goal.servlet.handlers.GoalRetrievalHandler;
import dev.personal.financial.tracker.controller.transaction.servlet.TransactionServlet;
import dev.personal.financial.tracker.controller.transaction.servlet.handlers.TransactionRequestRouter;
import dev.personal.financial.tracker.controller.transaction.servlet.handlers.TransactionRetrievalHandler;
import dev.personal.financial.tracker.controller.user.servlet.UserServlet;
import dev.personal.financial.tracker.controller.user.servlet.handlers.UserRequestRouter;
import dev.personal.financial.tracker.controller.user.servlet.handlers.UserRetrievalHandler;
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
import dev.personal.financial.tracker.util.DependencyInjector;

import jakarta.validation.Validator;

import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class ServletFactory {
    private final DependencyInjector injector;

    public TransactionServlet createTransactionServlet() throws SQLException, ClassNotFoundException {
        TransactionRepository transactionRepository = injector.createTransactionRepository();
        TransactionService transactionService = injector.createTransactionService(transactionRepository);
        Validator validator = injector.createValidator();
        TransactionRetrievalHandler transactionRetrievalHandler = injector.createTransactionRetrievalHandler(
                transactionService);
        TransactionRequestRouter requestRouter = injector.createTransactionRequestRouter(transactionRetrievalHandler);

        return new TransactionServlet(transactionService, validator, requestRouter);
    }

    public UserServlet createUserServlet() throws SQLException, ClassNotFoundException {
        UserRepository userRepository = injector.createUserRepository();
        UserService userService = injector.createUserService(userRepository);
        Validator validator = injector.createValidator();
        UserRetrievalHandler retrievalHandler = injector.createUserRetrievalHandler(userService);
        UserRequestRouter requestRouter = injector.createUserRequestRouter(retrievalHandler);

        return new UserServlet(userService, validator, requestRouter);
    }

    public GoalServlet createGoalServlet() throws SQLException, ClassNotFoundException {
        GoalRepository goalRepository = injector.createGoalRepository();
        GoalService goalService = injector.createGoalService(goalRepository);
        Validator validator = injector.createValidator();
        GoalRetrievalHandler retrievalHandler = injector.createGoalRetrievalHandler(goalService);
        GoalRequestRouter requestRouter = injector.createGoalRequestRouter(retrievalHandler);

        return new GoalServlet(goalService, validator, requestRouter);
    }

    public BudgetServlet createBudgetServlet() throws SQLException, ClassNotFoundException {
        BudgetRepository budgetRepository = injector.createBudgetRepository();
        BudgetService budgetService = injector.createBudgetService(budgetRepository);
        Validator validator = injector.createValidator();
        BudgetRetrievalHandler retrievalHandler = injector.createBudgetRetrievalHandler(budgetService);
        BudgetRequestRouter requestRouter = injector.createBudgetRequestRouter(retrievalHandler);

        return new BudgetServlet(budgetService, validator, requestRouter);
    }

    public AdminServlet createAdminServlet() throws SQLException, ClassNotFoundException {
        AdminRepository adminRepository = injector.createAdminRepository();
        AdminService adminService = injector.createAdminService(adminRepository);
        Validator validator = injector.createValidator();
        AdminRetrievalHandler retrievalHandler = injector.createAdminRetrievalHandler(adminService);
        AdminRequestRouter requestRouter = injector.createAdminRequestRouter(retrievalHandler);

        return new AdminServlet(adminService, validator, requestRouter);
    }
}
