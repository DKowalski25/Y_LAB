package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.controller.transaction.TransactionController;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.exception.budget.BudgetNotFoundException;
import dev.personal.financial.tracker.model.TransactionCategory;
import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.controller.budget.BudgetController;
import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.dto.user.UserOut;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
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

        BigDecimal monthlyBudget = printer.readBigDecimal("Введите месячный бюджет:");
        if (monthlyBudget == null) {
            printer.printInfo("Установка бюджета отменена.");
            return;
        }

        int id = UUID.randomUUID().hashCode();

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

        try {
            BudgetOut budgetOut = budgetController.getBudgetByUserId(user.getId());
            printer.printWithDivider("Ваш месячный бюджет: " + budgetOut.getMonthlyBudget());
        } catch (BudgetNotFoundException e) {
            printer.printInfo("Бюджет не установлен.");
        }
    }

    public void calculateCurrentBalance(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        List<TransactionOut> transactions = transactionController.getTransactionsByUserId(user.getId());

        BigDecimal balance = BigDecimal.ZERO;

        for (TransactionOut transaction : transactions) {
            if (transaction.isIncome()) {
                balance = balance.add(transaction.getAmount());
            } else {
                balance = balance.subtract(transaction.getAmount());
            }
        }

        printer.printWithDivider("Текущий баланс:");
        printer.printInfo("Баланс: " + balance);
    }

    public void calculateIncomeAndExpenses(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        LocalDate startDate = printer.readDate("Введите начальную дату (гггг-мм-дд):");
        if (startDate == null) {
            printer.printInfo("Расчёт доходов и расходов отменен.");
            return;
        }

        LocalDate endDate = printer.readDate("Введите конечную дату (гггг-мм-дд):");
        if (endDate == null) {
            printer.printInfo("Расчёт доходов и расходов отменен.");
            return;
        }

        List<TransactionOut> transactions = transactionController.getTransactionsByUserIdAndDateRange(
                user.getId(), startDate, endDate);

        BigDecimal totalIncome = transactions.stream()
                .filter(TransactionOut::isIncome)
                .map(TransactionOut::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);;

        BigDecimal totalExpenses = transactions.stream()
                .filter(t -> !t.isIncome())
                .map(TransactionOut::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);;

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

        Map<TransactionCategory, BigDecimal> expensesByCategory = expenses.stream()
                .collect(Collectors.groupingBy(
                        TransactionOut::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, TransactionOut::getAmount, BigDecimal::add)));

        printer.printWithDivider("Анализ расходов по категориям:");
        if (expensesByCategory.isEmpty()) {
            printer.printInfo("Расходы не найдены.");
        } else {
            expensesByCategory.forEach((category, amount) ->
                    printer.printInfo(category + ": " + amount));
        }
    }

    public void generateFinancialReport(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        BigDecimal balance = BigDecimal.ZERO;

        List<TransactionOut> transactions = transactionController.getTransactionsByUserId(user.getId());

        for (TransactionOut transaction : transactions) {
            if (transaction.isIncome()) {
                balance = balance.add(transaction.getAmount());
            } else {
                balance = balance.subtract(transaction.getAmount());
            }
        }


        try {
            BudgetOut budget = budgetController.getBudgetByUserId(user.getId());

            List<TransactionOut> expenses = transactionController.getTransactionsByUserIdAndType(user.getId(), false);

            Map<TransactionCategory, BigDecimal> expensesByCategory = expenses.stream()
                    .collect(Collectors.groupingBy(
                            TransactionOut::getCategory,
                            Collectors.reducing(BigDecimal.ZERO,TransactionOut::getAmount, BigDecimal::add)
                    ));

            printer.printWithDivider("Финансовый отчёт:");
            printer.printInfo("Текущий баланс: " + balance);

            if (budget != null) {
                printer.printInfo("Месячный бюджет: " + budget.getMonthlyBudget());
            } else {
                printer.printInfo("Месячный бюджет не установлен.");
            }

            printer.printInfo("Расходы по категориям:");
            if (expensesByCategory.isEmpty()) {
                printer.printInfo("Расходы не найдены.");
            } else {
                expensesByCategory.forEach((category, amount) ->
                        printer.printInfo(category + ": " + amount));
            }
        } catch (BudgetNotFoundException e) {
            printer.printError(e.getMessage());
        }
    }
}