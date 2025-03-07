package dev.personal.financial.tracker.controller.goal;

import dev.personal.financial.tracker.model.Goal;
import dev.personal.financial.tracker.service.goal.GoalService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GoalControllerImpl implements GoalController {
    private final GoalService goalService;

    @Override
    public void addGoal(Goal goal) {
        goalService.addGoal(goal);
    }

    @Override
    public List<Goal> getGoalsByUserId(String userId) {
        return goalService.getGoalsByUserId(userId);
    }

    @Override
    public void updateGoal(Goal goal) {
        goalService.updateGoal(goal);
    }

    @Override
    public void deleteGoal(String userId) {
        goalService.deleteGoal(userId);
    }
}