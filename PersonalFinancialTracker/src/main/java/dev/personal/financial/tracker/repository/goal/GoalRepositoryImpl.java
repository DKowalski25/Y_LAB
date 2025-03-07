package dev.personal.financial.tracker.repository.goal;

import dev.personal.financial.tracker.model.Goal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GoalRepositoryImpl implements GoalRepository {
    private final Map<String, Goal> goals = new HashMap<>();

    @Override
    public void save(Goal goal) {
        goals.put(goal.getUserId(), goal);
    }

    @Override
    public List<Goal> findByUserId(String userId) {
        return goals.values().stream()
                .filter(g -> g.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public void update(Goal goal) {
        goals.put(goal.getUserId(), goal);
    }

    @Override
    public void delete(String userId) {
        goals.remove(userId);
    }
}