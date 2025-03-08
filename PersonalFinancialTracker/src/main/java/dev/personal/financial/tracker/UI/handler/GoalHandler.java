package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.controller.goal.GoalController;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;

import dev.personal.financial.tracker.dto.user.UserOut;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GoalHandler {
    private final GoalController goalController;
    private final ConsolePrinter printer;

    public void addGoal(UserOut user) {
        if (user == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        GoalOut existingGoal = goalController.getGoalsByUserId(user.getId());
        if (existingGoal != null) {
            printer.printError("У вас уже есть цель. Сначала удалите текущую цель.");
            return;
        }

        String goalName = printer.readNonEmptyString("Введите название цели:");
        double targetAmount = printer.readDouble("Введите требуемую сумму:");

        String id = UUID.randomUUID().toString();

        GoalIn goalIn = new GoalIn(
                id,
                user.getId(),
                goalName,
                targetAmount
        );

        goalController.addGoal(goalIn);
        printer.printSuccess("Цель успешно добавлена.");
    }

    public void viewGoals(UserOut user) {
        if (user == null) {
            printer.printError("Пользователь не авторизован.");
            return;
        }

        GoalOut goal = goalController.getGoalsByUserId(user.getId());
        if (goal == null) {
            printer.printInfo("Цель не установлена.");
        } else {
            printer.printWithDivider("Ваша цель:");
            double progress = goalController.getProgress(goal.getId());
            printer.printInfo("Цель: " + goal.getGoalName());
            printer.printInfo("Целевая сумма: " + goal.getGoalAmount());
            printer.printInfo("Накоплено: " + goal.getSavedAmount());
            printer.printInfo("Прогресс: " + progress + "%");
            printer.printWithDivider("");
        }
    }

    public void deleteGoal(UserOut userOut) {
        if (userOut == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        goalController.deleteGoalByUserId(userOut.getId());
        printer.printSuccess("Цель успешно удалена.");

    }
}