package dev.personal.financial.tracker.repository.budget;

import dev.personal.financial.tracker.exception.budget.BudgetAlreadyExistsException;
import dev.personal.financial.tracker.exception.budget.BudgetNotFoundException;
import dev.personal.financial.tracker.model.Budget;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация интерфейса {@link BudgetRepository}.
 * Хранит бюджеты в памяти с использованием HashMap.
 */
public class BudgetRepositoryImpl implements BudgetRepository {
    private final Map<String, Budget> budgets = new HashMap<>();

    @Override
    public void save(Budget budget) {
        if (budgets.containsKey(budget.getUserId())) {
            throw new BudgetAlreadyExistsException(budget.getUserId());
        }
        budgets.put(budget.getUserId(), budget);
    }

    @Override
    public Budget findByUserId(String userId) {
        Budget budget = budgets.get(userId);
        if (budget == null) {
            throw new BudgetNotFoundException(userId);
        }
        return budget;
    }

    @Override
    public void update(Budget budget) {
        if (!budgets.containsKey(budget.getUserId())) {
            throw new BudgetNotFoundException(budget.getUserId());
        }
        budgets.put(budget.getUserId(), budget);
    }

    @Override
    public void delete(String userId) {
        if (!budgets.containsKey(userId)) {
            throw new BudgetNotFoundException(userId);
        }
        budgets.remove(userId);
    }
}