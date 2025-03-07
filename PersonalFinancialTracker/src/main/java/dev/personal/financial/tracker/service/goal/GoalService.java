package dev.personal.financial.tracker.service.goal;

import dev.personal.financial.tracker.model.Goal;

import java.util.List;

public interface GoalService {
    void addGoal(Goal goal);
    List<Goal> getGoalsByUserId(String userId);
    void updateGoal(Goal goal);
    void deleteGoal(String userId);
}