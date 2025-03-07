package dev.personal.financial.tracker.repository.goal;

import dev.personal.financial.tracker.model.Goal;

import java.util.List;

public interface GoalRepository {
    void save(Goal goal);
    List<Goal> findByUserId(String userId);
    void update(Goal goal);
    void delete(String userId);
}