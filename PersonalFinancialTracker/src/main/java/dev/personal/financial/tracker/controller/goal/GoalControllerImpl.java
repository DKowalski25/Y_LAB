package dev.personal.financial.tracker.controller.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.exception.goal.GoalAlreadyExistsException;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;
import dev.personal.financial.tracker.service.goal.GoalService;

import lombok.RequiredArgsConstructor;

/**
 * Реализация интерфейса {@link GoalController}.
 * Обрабатывает HTTP-запросы, связанные с целями.
 */
@RequiredArgsConstructor
public class GoalControllerImpl implements GoalController {
    private final GoalService goalService;

    @Override
    public void addGoal(GoalIn goalIn) {
        try {
            goalService.addGoal(goalIn);
        } catch (GoalAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public GoalOut getGoalsByUserId(String userId) {
        try {
            return goalService.getGoalsByUserId(userId);
        } catch (GoalNotFoundException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void updateGoal(GoalIn goalIn) {
        try {
            goalService.updateGoal(goalIn);
        } catch (GoalNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void deleteGoalByUserId(String userId) {
        try {
            goalService.deleteGoalByUserId(userId);
        } catch (GoalNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void updateSavedAmount(String goalId, double amount) {
        try {
            goalService.updateSavedAmount(goalId, amount);
        } catch (GoalNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public double getProgress(String goalId) {
        try {
            return goalService.getProgress(goalId);
        } catch (GoalNotFoundException e) {
            System.err.println(e.getMessage());
            return 0.0;
        }
    }
}