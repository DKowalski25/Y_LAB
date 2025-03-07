package dev.personal.financial.tracker.controller.budget;

import dev.personal.financial.tracker.model.Budget;
import dev.personal.financial.tracker.service.budget.BudgetService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BudgetControllerImpl implements BudgetController {
    private final BudgetService budgetService;

    @Override
    public void setBudget(Budget budget) {
        budgetService.setBudget(budget);
    }

    @Override
    public Budget getBudgetByUserId(String userId) {
        return budgetService.getBudgetByUserId(userId);
    }

    @Override
    public void updateBudget(Budget budget) {
        budgetService.updateBudget(budget);
    }

    @Override
    public void deleteBudget(String userId) {
        budgetService.deleteBudget(userId);
    }
}