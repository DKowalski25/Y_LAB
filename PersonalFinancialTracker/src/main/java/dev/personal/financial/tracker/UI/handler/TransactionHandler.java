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
            printer.printError("Пользователь не авторизован.");
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
}