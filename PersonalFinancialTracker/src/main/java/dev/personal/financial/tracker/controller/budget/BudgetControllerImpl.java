package dev.personal.financial.tracker.controller.budget;

import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.exception.budget.BudgetAlreadyExistsException;
import dev.personal.financial.tracker.exception.budget.BudgetNotFoundException;
import dev.personal.financial.tracker.service.budget.BudgetService;

import lombok.RequiredArgsConstructor;

/**
 * Реализация интерфейса {@link BudgetController}.
 */
@RequiredArgsConstructor
public class BudgetControllerImpl implements BudgetController {
    private final BudgetService budgetService;

    @Override
    public void setBudget(BudgetIn budgetIn) {
        try {
            budgetService.setBudget(budgetIn);
        } catch (BudgetAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public BudgetOut getBudgetByUserId(String userId) {
        try {
            return budgetService.getBudgetByUserId(userId);
        } catch (BudgetNotFoundException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void updateBudget(BudgetIn budgetIn) {
        try {
            budgetService.updateBudget(budgetIn);
        } catch (BudgetNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void deleteBudget(String userId) {
        try {
            budgetService.deleteBudget(userId);
        } catch (BudgetNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}