package dev.personal.financial.tracker.controller.budget;

import dev.personal.financial.tracker.model.Budget;

public interface BudgetController {
    void setBudget(Budget budget);
    Budget getBudgetByUserId(String userId);
    void updateBudget(Budget budget);
    void deleteBudget(String userId);
}