package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.controller.budget.BudgetController;
import dev.personal.financial.tracker.controller.goal.GoalController;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.exception.budget.BudgetNotFoundException;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;
import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;
import dev.personal.financial.tracker.model.TransactionCategory;
import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.controller.transaction.TransactionController;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.dto.user.UserOut;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
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

        BigDecimal amount = printer.readBigDecimal("Введите сумму транзакции:");
        if (amount == null) {
            printer.printInfo("Добавление транзакции отменено.");
            return;
        }

        TransactionCategory category = printer.readTransactionCategory("Введите категорию транзакции:");
        if (category == null) {
            printer.printInfo("Добавление транзакции отменено.");
            return;
        }

        String description = printer.readNonEmptyString("Введите описание транзакции:");
        if (description == null) {
            printer.printInfo("Добавление транзакции отменено.");
            return;
        }
        Boolean isIncome = printer.readBoolean("Это доход?");
        if (isIncome == null) {
            printer.printInfo("Добавление транзакции отменено.");
            return;
        }

        if (!isIncome) {
            try {
                BudgetOut budgetOut = budgetController.getBudgetByUserId(user.getId());
                BigDecimal totalExpenses = transactionController.getTotalExpensesForCurrentMonth(user.getId());
                if (totalExpenses.add(amount).compareTo(budgetOut.getMonthlyBudget()) > 0) {
                    printer.printInfo("Вы превысили месячный бюджета.");
                    return;
                }
            } catch (BudgetNotFoundException ignored) {}
        }

        if (isIncome) {
            try {
                GoalOut goal = goalController.getGoalsByUserId(user.getId());
                goalController.updateSavedAmount(goal.getId(), amount);
                double progress = goalController.getProgress(goal.getId());
                notifyProgress(goal, progress);
            } catch (GoalNotFoundException ignored) {}
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
    }

    public void editTransaction(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
        }

        Integer transactionId = printer.readInt("Введите id транзакции:");
        if (transactionId == null) {
            printer.printInfo("Редактирование транзакции отменено.");
            return;
        }

        BigDecimal amount = printer.readBigDecimal("Введите сумму транзакции:");
        if (amount == null) {
            printer.printInfo("Редактирование транзакции отменено.");
            return;
        }

        TransactionCategory category = printer.readTransactionCategory("Введите категорию транзакции:");
        if (category == null) {
            printer.printInfo("Редактирование транзакции отменено.");
            return;
        }

        String description = printer.readNonEmptyString("Введите описание транзакции:");
        if (description == null) {
            printer.printInfo("Редактирование транзакции отменено.");
            return;
        }

        Boolean isIncome = printer.readBoolean("Это доход?");
        if (isIncome == null) {
            printer.printInfo("Редактирование транзакции отменено.");
            return;
        }

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

        int choice = printer.readIntMenu("Выберите действие:");

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

        int choice = printer.readIntMenu("Выберите номер действие:");

        List<TransactionOut> transactions;
        switch (choice) {
            case 1:
                String category = printer.readNonEmptyString("Введите категорию:");
                if (category == null) {
                    printer.printInfo("Фильтрация отменена.");
                    return;
                }
                transactions = transactionController.getTransactionsByUserIdAndCategory(user.getId(), category);
                break;
            case 2:
                LocalDate date = printer.readDate("Введите дату (гггг-мм-дд):");
                if (date == null) {
                    printer.printInfo("Фильтрация отменена.");
                    return;
                }
                transactions = transactionController.getTransactionsByUserIdAndDate(user.getId(), date);
                break;
            case 3:
                Boolean isIncome = printer.readBoolean("Это доход?");
                if (isIncome == null) {
                    printer.printInfo("Фильтрация отменена.");
                    return;
                }
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
        try {
            int transactionId = printer.readInt("Введите ID транзакции для удаления:");
            transactionController.deleteTransaction(transactionId);
        } catch (TransactionNotFoundException e) {
            printer.printError("Ошибка: " + e.getMessage());
        }

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