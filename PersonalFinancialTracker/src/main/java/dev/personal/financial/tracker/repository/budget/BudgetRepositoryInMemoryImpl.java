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
public class BudgetRepositoryInMemoryImpl implements BudgetRepository {
    private final Map<Integer, Budget> budgets = new HashMap<>();

    @Override
    public void save(Budget budget) {
        if (budgets.containsKey(budget.getUserId())) {
            throw new BudgetAlreadyExistsException(budget.getUserId());
        }
        budgets.put(budget.getUserId(), budget);
    }

    @Override
    public Budget findByUserId(int userId) {
        Budget budget = budgets.get(userId);
        if (budget == null) {
            throw new BudgetNotFoundException(userId);
        }
        return budget;
    }

    @Override
    public void update(Budget budget) {
        budgets.put(budget.getUserId(), budget);
    }

    @Override
    public void delete(int userId) {
        budgets.remove(userId);
    }
}