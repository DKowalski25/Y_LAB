package dev.personal.financial.tracker.controller.budget;

import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.service.budget.BudgetService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BudgetControllerImpl implements BudgetController {
    private final BudgetService budgetService;

    @Override
    public void setBudget(BudgetIn budgetIn) {
        budgetService.setBudget(budgetIn);
    }

    @Override
    public BudgetOut getBudgetByUserId(String userId) {
        return budgetService.getBudgetByUserId(userId);
    }

    @Override
    public void updateBudget(BudgetIn budgetIn) {
        budgetService.updateBudget(budgetIn);
    }

    @Override
    public void deleteBudget(String userId) {
        budgetService.deleteBudget(userId);
    }
}