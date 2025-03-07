package dev.personal.financial.tracker.repository.budget;

import dev.personal.financial.tracker.model.Budget;

import java.util.HashMap;
import java.util.Map;

public class BudgetRepositoryImpl implements BudgetRepository {
    private final Map<String, Budget> budgets = new HashMap<>();
    @Override
    public void save(Budget budget) {
        budgets.put(budget.getUserId(), budget);
    }

    @Override
    public Budget findByUserId(String userId) {
        return budgets.get(userId);
    }

    @Override
    public void update(Budget budget) {
        budgets.put(budget.getUserId(), budget);
    }

    @Override
    public void delete(String userId) {
        budgets.remove(userId);
    }
}