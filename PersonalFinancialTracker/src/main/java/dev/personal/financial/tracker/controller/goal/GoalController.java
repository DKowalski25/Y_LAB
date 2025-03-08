package dev.personal.financial.tracker.controller.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.model.Goal;

import java.util.List;

public interface GoalController {
    void addGoal(GoalIn goalIn);
    List<GoalOut> getGoalsByUserId(String userId);
    void updateGoal(GoalIn goalIn);
    void deleteGoal(String userId);
}