package dev.personal.financial.tracker.repository.goal;

import dev.personal.financial.tracker.model.Goal;

import java.util.HashMap;
import java.util.Map;

public class GoalRepositoryImpl implements GoalRepository {
    private final Map<String, Goal> goals = new HashMap<>();

    @Override
    public void save(Goal goal) {
        goals.put(goal.getId(), goal);
    }

    @Override
    public Goal findById(String id) {
        return goals.get(id);
    }

    @Override
    public Goal findByUserId(String userId) {
        return goals.values().stream()
                .filter(g -> g.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Goal goal) {
        goals.put(goal.getUserId(), goal);
    }

    @Override
    public void delete(String userId) {
        goals.remove(userId);
    }

    @Override
    public void updateSavedAmount(String goalId, double amount) {
        Goal goal = goals.get(goalId);
        if (goal != null) {
            goal.setSavedAmount(goal.getSavedAmount() + amount);
            update(goal);
        }
    }

    @Override
    public double getSavedAmount(String goalId) {
        Goal goal = goals.get(goalId);
        return goal != null ? goal.getSavedAmount() : 0.0;
    }
}