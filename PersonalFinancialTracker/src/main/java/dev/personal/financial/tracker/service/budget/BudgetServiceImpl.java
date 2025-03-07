package dev.personal.financial.tracker.service.budget;

import dev.personal.financial.tracker.model.Budget;
import dev.personal.financial.tracker.repository.budget.BudgetRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;

    @Override
    public void setBudget(Budget budget) {
        budgetRepository.save(budget);
    }

    @Override
    public Budget getBudgetByUserId(String userId) {
        return budgetRepository.findByUserId(userId);
    }

    @Override
    public void updateBudget(Budget budget) {
        budgetRepository.update(budget);
    }

    @Override
    public void deleteBudget(String userId) {
        budgetRepository.delete(userId);
    }
}