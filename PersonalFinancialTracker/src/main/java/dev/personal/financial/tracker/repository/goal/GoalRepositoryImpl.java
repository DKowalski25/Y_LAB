package dev.personal.financial.tracker.repository.goal;

import dev.personal.financial.tracker.exception.goal.GoalAlreadyExistsException;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;
import dev.personal.financial.tracker.model.Goal;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация интерфейса {@link GoalRepository}.
 * Хранит цели в памяти с использованием HashMap.
 */
public class GoalRepositoryImpl implements GoalRepository {
    private final Map<String, Goal> goals = new HashMap<>();

    @Override
    public void save(Goal goal) {
        if (goals.containsKey(goal.getId())) {
            throw new GoalAlreadyExistsException(goal.getId());
        }
        goals.put(goal.getId(), goal);
    }

    @Override
    public Goal findById(String id) {
        Goal goal = goals.get(id);
        if (goal == null) {
            throw new GoalNotFoundException(id);
        }
        return goal;
    }

    @Override
    public Goal findByUserId(String userId) {
        Goal goal = goals.values().stream()
                .filter(g -> g.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
        if (goal == null) {
            throw new GoalNotFoundException(userId);
        }
        return goal;
    }

    @Override
    public void update(Goal goal) {
        if (!goals.containsKey(goal.getId())) {
            throw new GoalNotFoundException(goal.getId());
        }
        goals.put(goal.getId(), goal);
    }

    @Override
    public void deleteByUserId(String userId) {
        Goal goal = findByUserId(userId);
        goals.values().removeIf(g -> g.getUserId().equals(userId));
    }

    @Override
    public void updateSavedAmount(String goalId, double amount) {
        Goal goal = findById(goalId);
        goal.setSavedAmount(goal.getSavedAmount() + amount);
        update(goal);
    }

    @Override
    public double getSavedAmount(String goalId) {
        Goal goal = findById(goalId);
        return goal.getSavedAmount();
    }
}