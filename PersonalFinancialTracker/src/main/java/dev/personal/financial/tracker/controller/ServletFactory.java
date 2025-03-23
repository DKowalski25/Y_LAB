package dev.personal.financial.tracker.controller;

import dev.personal.financial.tracker.controller.transaction.servlet.TransactionServlet;
import dev.personal.financial.tracker.controller.transaction.servlet.handlers.TransactionRequestRouter;
import dev.personal.financial.tracker.controller.transaction.servlet.handlers.TransactionRetrievalHandler;
import dev.personal.financial.tracker.controller.user.servlet.UserServlet;
import dev.personal.financial.tracker.controller.user.servlet.handlers.UserRequestRouter;
import dev.personal.financial.tracker.controller.user.servlet.handlers.UserRetrievalHandler;
import dev.personal.financial.tracker.repository.transaction.TransactionRepository;
import dev.personal.financial.tracker.repository.user.UserRepository;
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
}
