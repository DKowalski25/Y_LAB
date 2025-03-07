package dev.personal.financial.tracker.service.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.model.Goal;

import java.util.List;

public interface GoalService {
    void addGoal(GoalIn goalIn);
    GoalOut getGoalById(String id);
    List<GoalOut> getGoalsByUserId(String userId);
    void updateGoal(GoalIn goalIn);
    void deleteGoal(String userId);
}