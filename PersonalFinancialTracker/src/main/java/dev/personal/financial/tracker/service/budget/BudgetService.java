package dev.personal.financial.tracker.service.budget;

import dev.personal.financial.tracker.model.Budget;

public interface BudgetService {
    void setBudget(Budget budget);
    Budget getBudgetByUserId(String userId);
    void updateBudget(Budget budget);
    void deleteBudget(String userId);
}