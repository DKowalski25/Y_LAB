package dev.personal.financial.tracker.repository.goal;

import dev.personal.financial.tracker.exception.goal.GoalAlreadyExistsException;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;
import dev.personal.financial.tracker.model.Goal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализация интерфейса {@link GoalRepository}.
 * Хранит цели в памяти с использованием HashMap.
 */
public class GoalRepositoryInMemoryImpl implements GoalRepository {
    private final Map<Integer, Goal> goals = new HashMap<>();

    @Override
    public void save(Goal goal) {
        if (goals.containsKey(goal.getId())) {
            throw new GoalAlreadyExistsException(goal.getId());
        }
        goals.put(goal.getId(), goal);
    }

    @Override
    public Goal findById(int id) {
        Goal goal = goals.get(id);
        if (goal == null) {
            throw new GoalNotFoundException();
        }
        return goal;
    }

    @Override
    public Goal findByUserId(int userId) {
        return goals.values().stream()
                .filter(g -> g.getUserId() == (userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Goal goal) {
        goals.put(goal.getId(), goal);
    }

    @Override
    public void deleteByUserId(int userId) {
        goals.values().removeIf(g -> g.getUserId() == (userId));
    }

    @Override
    public void updateSavedAmount(int goalId, BigDecimal amount) {
        Goal goal = findById(goalId);
        goal.setSavedAmount(goal.getSavedAmount().add(amount));
        update(goal);
    }

    @Override
    public BigDecimal getSavedAmount(int goalId) {
        Goal goal = findById(goalId);
        return goal.getSavedAmount();
    }
}