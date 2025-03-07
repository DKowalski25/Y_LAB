package dev.personal.financial.tracker.UI.menu;

import dev.personal.financial.tracker.UI.handler.BudgetHandler;
import dev.personal.financial.tracker.UI.handler.GoalHandler;
import dev.personal.financial.tracker.UI.handler.TransactionHandler;
import dev.personal.financial.tracker.model.User;

import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class UserMenu {
    private final TransactionHandler transactionHandler;
    private final GoalHandler goalHandler;
    private final BudgetHandler budgetHandler;
    private final Scanner sc;

    public void run(User user) {
        while (true) {
            System.out.println("\nМеню пользователя:");
            System.out.println("1. Добавить транзакцию");
            System.out.println("2. Просмотреть транзакции");
            System.out.println("3. Установить цель");
            System.out.println("4. Просмотреть цели");
            System.out.println("5. Установить бюджет");
            System.out.println("6. Просмотреть бюджет");
            System.out.println("7. Выйти из аккаунта");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    transactionHandler.addTransaction(user);
                    break;
                case 2:
                    transactionHandler.viewTransactions(user);
                    break;
                case 3:
                    goalHandler.addGoal(user);
                    break;
                case 4:
                    goalHandler.viewGoals(user);
                    break;
                case 5:
                    budgetHandler.setBudget(user);
                    break;
                case 6:
                    budgetHandler.viewBudget(user);
                    break;
                case 7:
                    System.out.println("Вы успешно вышли из аккаунта.");
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }
}