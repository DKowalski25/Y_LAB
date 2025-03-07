package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.controller.budget.BudgetController;
import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;

import lombok.RequiredArgsConstructor;

import java.util.Scanner;
import java.util.UUID;

@RequiredArgsConstructor
public class BudgetHandler {
    private final BudgetController budgetController;
    private final Scanner sc;

    public void setBudget(UserOut user) {
        if (user == null) {
            System.out.println("Пользователь не авторизован.");
            return;
        }

        System.out.println("Введите месячный бюджет:");
        double monthlyBudget = sc.nextDouble();
//        sc.nextLine(); // Очистка буфера

        String id = UUID.randomUUID().toString();

        BudgetIn budgetIn = new BudgetIn(id, user.getId(), monthlyBudget);
        budgetController.setBudget(budgetIn);
        System.out.println("Бюджет успешно установлен.");
    }

    public void viewBudget(UserOut user) {
        if (user == null) {
            System.out.println("Ошибка: пользователь не авторизован.");
            return;
        }

        BudgetOut budgetOut = budgetController.getBudgetByUserId(user.getId());
        if (budgetOut == null) {
            System.out.println("Бюджет не установлен.");
        } else {
            System.out.println("Ваш месячный бюджет: " + budgetOut.getMonthlyBudget());
        }
    }
}