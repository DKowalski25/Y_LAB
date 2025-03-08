package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.controller.budget.BudgetController;
import dev.personal.financial.tracker.controller.goal.GoalController;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.dto.goal.GoalOut;
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
    private final BudgetController budgetController;
    private final GoalController goalController;
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

        if (!isIncome) {
            BudgetOut budgetOut = budgetController.getBudgetByUserId(user.getId());
            if (budgetOut != null) {
                double totalExpenses = transactionController.getTotalExpensesForCurrentMonth(user.getId());
                if (totalExpenses + amount > budgetOut.getMonthlyBudget()) {
                    printer.printInfo("Вы превысили месячный бюджета.");
                    return;
                }
            }
        }

        if (isIncome) {
            List<GoalOut> goals = goalController.getGoalsByUserId(user.getId());
            for (GoalOut goal : goals) {
                goalController.updateSavedAmount(goal.getId(), amount);

                double progress = goalController.getProgress(goal.getId());
                notifyProgress(goal, progress);
            }
        }

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

        printer.printWithDivider("Выберите тип просмотра транзакций:");
        printer.printPrompt("1. Показать все транзакции");
        printer.printPrompt("2. Показать отфильтрованные транзакции");

        int choice = printer.readInt("Выберите действие:");

        switch (choice) {
            case 1:
                List<TransactionOut> allTransactions = transactionController.getTransactionsByUserId(user.getId());
                if (allTransactions.isEmpty()) {
                    printer.printInfo("Транзакции не найдены.");
                } else {
                    printer.printWithDivider("Список всех транзакций:");
                    for (TransactionOut transaction : allTransactions) {
                        System.out.println(transaction);
                    }
                }
                break;
            case 2:
                viewFilterTransactions(user);
                break;
            default:
                printer.printError("Неверный выбор. Пожалуйста, выберите действие из списка.");
        }
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

    public void deleteTransaction(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        String transactionId = printer.readNonEmptyString("Введите ID транзакции для удаления:");
        transactionController.deleteTransaction(transactionId);
        printer.printSuccess("Транзакция успешно удалена.");
    }

    private void notifyProgress(GoalOut goal, double progress) {
        if (progress >= 25 && progress < 50) {
            printer.printInfo("Вы достигли 25% цели '" + goal.getGoalName() + "'!");
        } else if (progress >= 50 && progress < 75) {
            printer.printInfo("Вы достигли 50% цели '" + goal.getGoalName() + "'!");
        } else if (progress >= 75 && progress < 95) {
            printer.printInfo("Вы достигли 75% цели '" + goal.getGoalName() + "'!");
        } else if (progress >= 95 && progress < 100) {
            printer.printInfo("Вы почти достигли цели '" + goal.getGoalName() + "'! Осталось совсем немного.");
        }
    }
}