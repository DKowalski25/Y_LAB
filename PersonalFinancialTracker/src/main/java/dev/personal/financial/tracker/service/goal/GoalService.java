package dev.personal.financial.tracker.service.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;

public interface GoalService {
    void addGoal(GoalIn goalIn);
    GoalOut getGoalById(String id);
    GoalOut getGoalsByUserId(String userId);
    void updateGoal(GoalIn goalIn);
    void deleteGoal(String userId);
    void updateSavedAmount(String userId, double amount);
    double getProgress(String userId);
}