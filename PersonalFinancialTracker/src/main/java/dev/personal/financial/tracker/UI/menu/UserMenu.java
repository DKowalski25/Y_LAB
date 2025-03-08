package dev.personal.financial.tracker.UI.menu;

import dev.personal.financial.tracker.UI.handler.BudgetHandler;
import dev.personal.financial.tracker.UI.handler.GoalHandler;
import dev.personal.financial.tracker.UI.handler.TransactionHandler;
import dev.personal.financial.tracker.UI.handler.UserHandler;
import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.dto.user.UserOut;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserMenu {
    private final TransactionHandler transactionHandler;
    private final GoalHandler goalHandler;
    private final BudgetHandler budgetHandler;
    private final UserHandler userHandler;
    private final ConsolePrinter printer;

    public void run(UserOut userOut) {
        while (true) {
            printer.printWithDivider("\nГлавное меню:");
            printer.printPrompt("1. Транзакции");
            printer.printPrompt("2. Цели");
            printer.printPrompt("3. Бюджет");
            printer.printPrompt("4. Профиль");
            printer.printPrompt("5. Выйти из аккаунта");

            int choice = printer.readInt("Выберите категорию:");

            switch (choice) {
                case 1:
                    runTransactionMenu(userOut);
                    break;
                case 2:
                    runGoalMenu(userOut);
                    break;
                case 3:
                    runBudgetMenu(userOut);
                    break;
                case 4:
                    runProfileMenu(userOut);
                    break;
                case 5:
                    printer.printInfo("Вы вышли из аккаунта.");
                    return;
                default:
                    printer.printError("Неверный выбор. Пожалуйста, выберите категорию из списка.");
            }
        }
    }

    private void runTransactionMenu(UserOut userOut) {
        while (true) {
            printer.printWithDivider("\nМеню транзакций:");
            printer.printPrompt("1. Добавить транзакцию");
            printer.printPrompt("2. Просмотреть транзакции");
            printer.printPrompt("3. Редактировать транзакцию");
            printer.printPrompt("4. Удалить транзакцию");
            printer.printPrompt("5. Вернуться в главное меню");

            int choice = printer.readInt("Выберите действие:");

            switch (choice) {
                case 1:
                    transactionHandler.addTransaction(userOut);
                    break;
                case 2:
                    transactionHandler.viewTransactions(userOut);
                    break;
                case 3:
                    transactionHandler.editTransaction(userOut);
                    break;
                case 4:
                    transactionHandler.deleteTransaction(userOut);
                    break;
                case 5:
                    return; // Возврат в главное меню
                default:
                    printer.printError("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }

    private void runGoalMenu(UserOut userOut) {
        while (true) {
            printer.printWithDivider("\nМеню целей:");
            printer.printPrompt("1. Установить цель");
            printer.printPrompt("2. Просмотреть цели");
            printer.printPrompt("3. Вернуться в главное меню");

            int choice = printer.readInt("Выберите действие:");

            switch (choice) {
                case 1:
                    goalHandler.addGoal(userOut);
                    break;
                case 2:
                    goalHandler.viewGoals(userOut);
                    break;
                case 3:
                    return; // Возврат в главное меню
                default:
                    printer.printError("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }

    private void runBudgetMenu(UserOut userOut) {
        while (true) {
            printer.printWithDivider("\nМеню бюджета:");
            printer.printPrompt("1. Установить бюджет");
            printer.printPrompt("2. Просмотреть бюджет");
            printer.printPrompt("3. Вернуться в главное меню");

            int choice = printer.readInt("Выберите действие:");

            switch (choice) {
                case 1:
                    budgetHandler.setBudget(userOut);
                    break;
                case 2:
                    budgetHandler.viewBudget(userOut);
                    break;
                case 3:
                    return; // Возврат в главное меню
                default:
                    printer.printError("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }

    private void runProfileMenu(UserOut userOut) {
        while (true) {
            printer.printWithDivider("\nМеню профиля:");
            printer.printPrompt("1. Редактировать профиль");
            printer.printPrompt("2. Удалить аккаунт");
            printer.printPrompt("3. Вернуться в главное меню");

            int choice = printer.readInt("Выберите действие:");

            switch (choice) {
                case 1:
                    userHandler.updateProfile(userOut.getEmail());
                    break;
                case 2:
                    userHandler.deleteAccount(userOut.getEmail());
                    return; // Выход из меню после удаления аккаунта
                case 3:
                    return; // Возврат в главное меню
                default:
                    printer.printError("Неверный выбор. Пожалуйста, выберите действие из списка.");
            }
        }
    }
}