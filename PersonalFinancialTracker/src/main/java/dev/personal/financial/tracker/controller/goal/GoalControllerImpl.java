package dev.personal.financial.tracker.controller.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.model.Goal;
import dev.personal.financial.tracker.service.goal.GoalService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GoalControllerImpl implements GoalController {
    private final GoalService goalService;

    @Override
    public void addGoal(GoalIn goalIn) {
        goalService.addGoal(goalIn);
    }

    @Override
    public List<GoalOut> getGoalsByUserId(String userId) {
        return goalService.getGoalsByUserId(userId);
    }

    @Override
    public void updateGoal(GoalIn goal) {
        goalService.updateGoal(goal);
    }

    @Override
    public void deleteGoal(String userId) {
        goalService.deleteGoal(userId);
    }

    @Override
    public void updateSavedAmount(String goalId, double amount) {
        goalService.updateSavedAmount(goalId, amount);
    }

    @Override
    public double getProgress(String goalId) {
        return goalService.getProgress(goalId);
    }
}