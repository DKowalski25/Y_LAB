package dev.personal.financial.tracker.UI.handler;

import dev.personal.financial.tracker.controller.goal.GoalController;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;

import dev.personal.financial.tracker.model.User;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@RequiredArgsConstructor
public class GoalHandler {
    private final GoalController goalController;
    private final Scanner sc;

    public void addGoal(User user) {
        if (user == null) {
            System.out.println("Пользователь не авторизован.");
            return;
        }

        System.out.println("Введите название цели:");
        String goalName = sc.next();

        System.out.println("Введите требуемую сумму:");
        double targetAmount = sc.nextDouble();

        String id = UUID.randomUUID().toString();

        GoalIn goalIn = new GoalIn(id, user.getId(), goalName, targetAmount);
        goalController.addGoal(goalIn);
        System.out.println("Цель успешно добавлена.");
    }

    public void viewGoals(User user) {
        if (user == null) {
            System.out.println("Пользователь не авторизован.");
            return;
        }

        List<GoalOut> goals = goalController.getGoalsByUserId(user.getId());
        if (goals.isEmpty()) {
            System.out.println("Цели не найдены.");
        } else {
            System.out.println("Список целей:");
            for (GoalOut goal : goals) {
                System.out.println(goal);
            }
        }
    }
}