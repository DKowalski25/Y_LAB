package dev.personal.financial.tracker.UI.menu;

import dev.personal.financial.tracker.UI.handler.BudgetHandler;
import dev.personal.financial.tracker.UI.handler.GoalHandler;
import dev.personal.financial.tracker.UI.handler.TransactionHandler;
import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.dto.user.UserOut;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserMenu {
    private final TransactionHandler transactionHandler;
    private final GoalHandler goalHandler;
    private final BudgetHandler budgetHandler;
    private final ConsolePrinter printer;

    public void run(UserOut userOut) {
        while (true) {
            printer.printWithDivider("\nМеню пользователя:");
            printer.printPrompt("1. Добавить транзакцию");
            printer.printPrompt("2. Просмотреть транзакции");
            printer.printPrompt("3. Установить цель");
            printer.printPrompt("4. Просмотреть цели");
            printer.printPrompt("5. Установить бюджет");
            printer.printPrompt("6. Просмотреть бюджет");
            printer.printPrompt("7. Выйти из аккаунта");

            int choice = printer.readInt("Выберите номер действие:");

            switch (choice) {
                case 1:
                    transactionHandler.addTransaction(userOut);
                    break;
                case 2:
                    transactionHandler.viewTransactions(userOut);
                    break;
                case 3:
                    goalHandler.addGoal(userOut);
                    break;
                case 4:
                    goalHandler.viewGoals(userOut);
                    break;
                case 5:
                    budgetHandler.setBudget(userOut);
                    break;
                case 6:
                    budgetHandler.viewBudget(userOut);
                    break;
                case 7:
                    printer.printInfo("Вы вышли из аккаунта.");
                    return;
                default:
                    printer.printError("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }
}