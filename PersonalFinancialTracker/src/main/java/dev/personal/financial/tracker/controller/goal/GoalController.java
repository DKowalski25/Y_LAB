package dev.personal.financial.tracker.controller.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;

public interface GoalController {
    void addGoal(GoalIn goalIn);
    GoalOut getGoalsByUserId(String userId);
    void updateGoal(GoalIn goalIn);
    void deleteGoal(String userId);
    void updateSavedAmount(String goalId, double amount);
    double getProgress(String goalId);
}