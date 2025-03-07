package dev.personal.financial.tracker.repository.budget;

import dev.personal.financial.tracker.model.Budget;

public interface BudgetRepository {
    void save(Budget budget);
    Budget findByUserId(String userId);
    void update(Budget budget);
    void delete(String userId);
}