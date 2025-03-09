package dev.personal.financial.tracker.service.budget;

import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetMapper;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.model.Budget;
import dev.personal.financial.tracker.repository.budget.BudgetRepository;

import lombok.RequiredArgsConstructor;

/**
 * Реализация интерфейса {@link BudgetService}.
 * Обрабатывает бизнес-логику, связанную с бюджетами.
 */
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;

    @Override
    public void setBudget(BudgetIn budgetIn) {
        Budget budget = BudgetMapper.toEntity(budgetIn);
        budgetRepository.save(budget);
    }

    @Override
    public BudgetOut getBudgetByUserId(String userId) {
        Budget budget = budgetRepository.findByUserId(userId);
        return BudgetMapper.toDto(budget);
    }

    @Override
    public void updateBudget(BudgetIn budgetIn) {
        Budget budget = budgetRepository.findByUserId(budgetIn.getUserId());
        BudgetMapper.updateEntity(budget, budgetIn);
        budgetRepository.update(budget);
    }

    @Override
    public void deleteBudget(String userId) {
        budgetRepository.delete(userId);
    }
}