package dev.personal.financial.tracker.controller.budget;

import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.model.Budget;

public interface BudgetController {
    void setBudget(BudgetIn budgetIn);
    BudgetOut getBudgetByUserId(String userId);
    void updateBudget(BudgetIn budgetIn);
    void deleteBudget(String userId);
}