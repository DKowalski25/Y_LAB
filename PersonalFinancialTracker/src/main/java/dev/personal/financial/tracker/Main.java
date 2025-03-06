package dev.personal.financial.tracker;

import dev.personal.financial.tracker.controller.transaction.TransactionController;
import dev.personal.financial.tracker.controller.transaction.TransactionControllerImpl;
import dev.personal.financial.tracker.controller.user.UserController;
import dev.personal.financial.tracker.controller.user.UserControllerImpl;
import dev.personal.financial.tracker.repository.transaction.TransactionRepository;
import dev.personal.financial.tracker.repository.transaction.TransactionRepositoryImpl;
import dev.personal.financial.tracker.repository.user.UserRepository;
import dev.personal.financial.tracker.repository.user.UserRepositoryImpl;
import dev.personal.financial.tracker.service.transaction.TransactionService;
import dev.personal.financial.tracker.service.transaction.TransactionServiceImpl;
import dev.personal.financial.tracker.service.user.UserService;
import dev.personal.financial.tracker.service.user.UserServiceImpl;
import dev.personal.financial.tracker.utils.ConsoleInterface;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        UserController userController = new UserControllerImpl(userService);

        TransactionRepository transactionRepository = new TransactionRepositoryImpl();
        TransactionService transactionService = new TransactionServiceImpl(transactionRepository);
        TransactionController transactionController = new TransactionControllerImpl(transactionService);

        ConsoleInterface consoleInterface = new ConsoleInterface(userController, transactionController);
        consoleInterface.run();
    }
}