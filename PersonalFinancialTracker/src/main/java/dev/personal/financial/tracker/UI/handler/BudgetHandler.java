package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.controller.transaction.TransactionController;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.controller.budget.BudgetController;
import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.dto.user.UserOut;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BudgetHandler {
    private final BudgetController budgetController;
    private final TransactionController transactionController;
    private final ConsolePrinter printer;

    public void setBudget(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        double monthlyBudget = printer.readDouble("Введите месячный бюджет:");

        String id = UUID.randomUUID().toString();

        BudgetIn budgetIn = new BudgetIn(
                id,
                user.getId(),
                monthlyBudget
        );

        budgetController.setBudget(budgetIn);
        printer.printSuccess("Бюджет успешно установлен.");
    }

    public void viewBudget(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        BudgetOut budgetOut = budgetController.getBudgetByUserId(user.getId());
        if (budgetOut == null) {
            printer.printInfo("Бюджет не установлен.");
        } else {
            printer.printWithDivider("Ваш месячный бюджет: " + budgetOut.getMonthlyBudget());
        }
    }

    public void calculateCurrentBalance(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        List<TransactionOut> transactions = transactionController.getTransactionsByUserId(user.getId());

        double balance = transactions.stream()
                .mapToDouble(t -> t.isIncome() ? t.getAmount() : -t.getAmount())
                .sum();

        printer.printWithDivider("Текущий баланс:");
        printer.printInfo("Баланс: " + balance);
    }

    public void calculateIncomeAndExpenses(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        LocalDate startDate = printer.readDate("Введите начальную дату (гггг-мм-дд):");
        LocalDate endDate = printer.readDate("Введите конечную дату (гггг-мм-дд):");

        List<TransactionOut> transactions = transactionController.getTransactionsByUserIdAndDateRange(
                user.getId(), startDate, endDate);

        double totalIncome = transactions.stream()
                .filter(TransactionOut::isIncome)
                .mapToDouble(TransactionOut::getAmount)
                .sum();

        double totalExpenses = transactions.stream()
                .filter(t -> !t.isIncome())
                .mapToDouble(TransactionOut::getAmount)
                .sum();

        printer.printWithDivider("Суммарные доходы и расходы за период:");
        printer.printInfo("Доходы: " + totalIncome);
        printer.printInfo("Расходы: " + totalExpenses);
        printer.printWithDivider("");
    }

    public void analyzeExpensesByCategory(UserOut user) {
        if (user == null) {
            printer.printError("Пользователь не авторизован.");
            return;
        }

        List<TransactionOut> expenses = transactionController.getTransactionsByUserIdAndType(user.getId(), false);

        Map<String, Double> expensesByCategory = expenses.stream()
                .collect(Collectors.groupingBy(
                        TransactionOut::getCategory,
                        Collectors.summingDouble(TransactionOut::getAmount)));

        printer.printWithDivider("Анализ расходов по категориям:");
        if (expensesByCategory.isEmpty()) {
            printer.printInfo("Расходы не найдены.");
        } else {
            expensesByCategory.forEach((category, amount) ->
                    printer.printInfo(category + ": " + amount));
        }
    }
}