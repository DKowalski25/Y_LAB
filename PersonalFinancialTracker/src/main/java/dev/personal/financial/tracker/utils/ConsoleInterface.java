package dev.personal.financial.tracker.utils;

import dev.personal.financial.tracker.controller.transaction.TransactionController;
import dev.personal.financial.tracker.controller.user.UserController;
import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.model.Transaction;
import dev.personal.financial.tracker.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


public class ConsoleInterface {
    private UserController userController;
    private TransactionController transactionController;
    private Scanner sc;
    private User currentUser;

    public ConsoleInterface(UserController userController, TransactionController transactionController) {
        this.userController = userController;
        this.transactionController = transactionController;
        this.sc = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Регистрация");
            System.out.println("2. Вход");
            System.out.println("3. Выход");

            int choice = sc.nextInt();
//            sc.nextInt();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }

    private void registerUser() {
        System.out.println("Введите имя пользователя:");
        String name = sc.next();

        System.out.println("Введите email:");
        String email = sc.next();

        System.out.println("Введите пароль:");
        String password = (String) sc.next();

        String id = UUID.randomUUID().toString();

        User user = new User(
                id,
                name,
                email,
                password,
                "USER"
        );
        userController.registerUser(user);
        System.out.println("Пользователь успешно зарегистрирован.");

    }

    private void loginUser() {
        // Переделать реализацию
        System.out.println("Введите email:");
        String email = sc.next();

        System.out.println("Введите пароль:");
        String password = sc.next();

        User user = userController.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Пользователь успешно авторизован.");
            userMenu();
        } else if (user == null){
            System.out.println("Пользователь с таким email не найден.");
        } else {
            System.out.println("Неверный пароль.");
        }
    }

    private void userMenu() {
        while (true) {
            System.out.println("\nМеню пользователя:");
            System.out.println("1. Добавить транзакцию");
            System.out.println("2. Просмотреть транзакции");
            System.out.println("3. Выйти из аккаунта");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    viewTransactions();
                    break;
                case 3:
                    currentUser = null;
                    System.out.println("Вы успешно вышли из аккаунта.");
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }

    private void addTransaction() {
        if (currentUser == null) {
            System.out.println("Пользователь не авторизован.");
            return;
        }

        System.out.println("Введите сумму транзакции:");
        double amount = sc.nextDouble();

        System.out.println("Введите категорию:");
        String category = sc.next();

        System.out.println("Введите описание:");
        String description = sc.next();

        System.out.println("Это доход? (true/false):");
        boolean isIncome = sc.nextBoolean();
        System.out.println("isIncome: " + isIncome);

        String id = UUID.randomUUID().toString();
        LocalDate date = LocalDate.now();

        Transaction transaction = new Transaction(
                id,
                currentUser.getId(),
                amount,
                category,
                date,
                description,
                isIncome
        );

        transactionController.addTransaction(transaction);
    }

    private void viewTransactions() {
        if (currentUser == null) {
            System.out.println("Ошибка: пользователь не авторизован.");
            return;
        }

        List<Transaction> transactions = transactionController.getTransactionsByUserId(currentUser.getId());
        if (transactions.isEmpty()) {
            System.out.println("Транзакции не найдены.");
        } else {
            System.out.println("Список транзакций:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}
