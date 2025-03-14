package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;
import dev.personal.financial.tracker.util.ConsolePrinter;
import dev.personal.financial.tracker.controller.goal.GoalController;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;

import dev.personal.financial.tracker.dto.user.UserOut;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
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

        try {
            GoalOut existingGoal = goalController.getGoalsByUserId(user.getId());
            printer.printError("У вас уже есть цель. Сначала удалите текущую цель.");
            return;
        } catch (GoalNotFoundException ignored) {

        };

        String goalName = printer.readNonEmptyString("Введите название цели:");
        if (goalName == null) {
            printer.printInfo("Добавление цели отменено.");
            return;
        }

        BigDecimal targetAmount = printer.readBigDecimal("Введите требуемую сумму:");
        if (targetAmount == null) {
            printer.printInfo("Добавление цели отменено.");
            return;
        }

        int id = UUID.randomUUID().hashCode();

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
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        try {
            GoalOut goal = goalController.getGoalsByUserId(user.getId());
            printer.printWithDivider("Ваша цель:");
            double progress = goalController.getProgress(goal.getId());
            printer.printInfo("Цель: " + goal.getGoalName());
            printer.printInfo("Целевая сумма: " + goal.getGoalAmount());
            printer.printInfo("Накоплено: " + goal.getSavedAmount());
            printer.printInfo("Прогресс: " + progress + "%");
            printer.printWithDivider("");
        } catch (GoalNotFoundException e) {
            printer.printInfo(e.getMessage());
        }
    }

    public void deleteGoal(UserOut userOut) {
        if (userOut == null) {
            printer.printError("Ошибка: пользователь не авторизован.");
            return;
        }

        try {
            goalController.deleteGoalByUserId(userOut.getId());
            printer.printSuccess("Цель успешно удалена.");
        } catch (GoalNotFoundException e) {
            printer.printError(e.getMessage());
        }
    }
}