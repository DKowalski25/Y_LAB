package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.controller.transaction.TransactionController;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.dto.user.UserOut;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class TransactionHandler {
    private final TransactionController transactionController;
    private final ConsolePrinter printer;

    public void addTransaction(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        double amount = printer.readDouble("Введите сумму транзакции:");
        String category = printer.readNonEmptyString("Введите категорию транзакции:");
        String description = printer.readNonEmptyString("Введите описание транзакции:");
        boolean isIncome = printer.readBoolean("Это доход?");

        TransactionIn transactionIn = new TransactionIn(
                user.getId(),
                amount,
                category,
                LocalDate.now(),
                description,
                isIncome
        );

        transactionController.addTransaction(transactionIn);
        printer.printSuccess("Транзакция успешно добавлена." + user.getId());
    }

    public void editTransaction(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
        }

        String transactionId = printer.readNonEmptyString("Введите id транзакции:");
        double amount = printer.readDouble("Введите сумму транзакции:");
        String category = printer.readNonEmptyString("Введите категорию транзакции:");
        String description = printer.readNonEmptyString("Введите описание транзакции:");
        boolean isIncome = printer.readBoolean("Это доход?");

        TransactionIn transactionIn = new TransactionIn(
                transactionId,
                amount,
                category,
                LocalDate.now(),
                description,
                isIncome
        );

        transactionController.updateTransaction(transactionId, transactionIn);
    }

    public void viewTransactions(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        List<TransactionOut> transactions = transactionController.getTransactionsByUserId(user.getId());
        if (transactions.isEmpty()) {
            printer.printInfo("Транзакции не найдены.");
        } else {
            printer.printWithDivider("Список транзакций:");
            for (TransactionOut transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    public void deleteTransaction(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        String transactionId = printer.readNonEmptyString("Введите id транзакции:");
        transactionController.deleteTransaction(transactionId);
        printer.printSuccess("Транзакция успешно удалена.");
    }

    public void viewFilterTransactions(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        printer.printWithDivider("Выберите тип фильтрации:");
        printer.printPrompt("1. По категории");
        printer.printPrompt("2. По дате");
        printer.printPrompt("3. По типу(доход/расход)");

        int choice = printer.readInt("Выберите номер действие:");

        List<TransactionOut> transactions;
        switch (choice) {
            case 1:
                String category = printer.readNonEmptyString("Введите категорию:");
                transactions = transactionController.getTransactionsByUserIdAndCategory(user.getId(), category);
                break;
            case 2:
                LocalDate date = printer.readDate("Введите дату (гггг-мм-дд):");
                transactions = transactionController.getTransactionsByUserIdAndDate(user.getId(), date);
                break;
            case 3:
                boolean isIncome = printer.readBoolean("Это доход?");
                transactions = transactionController.getTransactionsByUserIdAndType(user.getId(), isIncome);
                break;
            default:
                printer.printError("Неверный выбор. Пожалуйста, выберите действие из списка.");
                return;
        }

        if (transactions.isEmpty()) {
            printer.printInfo("Транзакции не найдены.");
        } else {
            printer.printWithDivider("Список транзакций:");
            for (TransactionOut transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}