package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.controller.transaction.TransactionController;

import dev.personal.financial.tracker.model.Transaction;
import dev.personal.financial.tracker.model.User;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@RequiredArgsConstructor
public class TransactionHandler {
    private final TransactionController transactionController;
    private final Scanner sc;

    public void addTransaction(User user) {
        if (user == null) {
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

        String id = UUID.randomUUID().toString();
        LocalDate date = LocalDate.now();

        Transaction transaction = new Transaction(
                id,
                user.getId(),
                amount,
                category,
                date,
                description,
                isIncome
        );

        transactionController.addTransaction(transaction);
        System.out.println("Транзакция успешно добавлена.");
    }

    public void viewTransactions(User user) {
        if (user == null) {
            System.out.println("Ошибка: пользователь не авторизован.");
            return;
        }

        List<Transaction> transactions = transactionController.getTransactionsByUserId(user.getId());
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