package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.controller.budget.BudgetController;
import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.dto.user.UserOut;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class BudgetHandler {
    private final BudgetController budgetController;
    private final ConsolePrinter printer;

    public void setBudget(UserOut user) {
        if (user == null) {
            printer.printError("Пользователь не авторизован.");
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
}