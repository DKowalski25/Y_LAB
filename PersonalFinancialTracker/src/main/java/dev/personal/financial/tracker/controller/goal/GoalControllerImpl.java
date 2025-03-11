package dev.personal.financial.tracker.controller.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.exception.goal.GoalAlreadyExistsException;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;
import dev.personal.financial.tracker.service.goal.GoalService;

import dev.personal.financial.tracker.util.ConsolePrinter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * Реализация интерфейса {@link GoalController}.
 */
@RequiredArgsConstructor
public class GoalControllerImpl implements GoalController {
    private final GoalService goalService;
    private final ConsolePrinter printer;

    @Override
    public void addGoal(GoalIn goalIn) {
        try {
            goalService.addGoal(goalIn);
        } catch (GoalAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public GoalOut getGoalsByUserId(int userId) {
        return goalService.getGoalsByUserId(userId);
    }

    @Override
    public void updateGoal(GoalIn goalIn) {
        try {
            goalService.updateGoal(goalIn);
        } catch (GoalNotFoundException e) {
            printer.printError(e.getMessage());
        }
    }

    @Override
    public void deleteGoalByUserId(int userId) {
        goalService.deleteGoalByUserId(userId);
    }

    @Override
    public void updateSavedAmount(int goalId, BigDecimal amount) {
        try {
            goalService.updateSavedAmount(goalId, amount);
        } catch (GoalNotFoundException e) {
            printer.printError(e.getMessage());
        }
    }

    @Override
    public double getProgress(int goalId) {
        try {
            return goalService.getProgress(goalId);
        } catch (GoalNotFoundException e) {
            printer.printError(e.getMessage());
            return 0;
        }
    }
}