package dev.personal.financial.tracker.repository.goal;

import dev.personal.financial.tracker.model.Goal;

public interface GoalRepository {
    void save(Goal goal);
    Goal findById(String id);
    Goal findByUserId(String userId);
    void update(Goal goal);
    void deleteByUserId(String userId);
    void updateSavedAmount(String goalId, double amount);
    double getSavedAmount(String goalId);
}