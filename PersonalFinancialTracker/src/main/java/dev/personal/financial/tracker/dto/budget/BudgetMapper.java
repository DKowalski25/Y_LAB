package dev.personal.financial.tracker.dto.budget;

import dev.personal.financial.tracker.model.Budget;

public class BudgetMapper {

    public static Budget toEntity(BudgetIn budgetIn) {
        return new Budget(
                budgetIn.getId(),
                budgetIn.getUserId(),
                budgetIn.getMonthlyBudget()
        );
    }

    public static BudgetOut toDto(Budget budget) {
        return new BudgetOut(
                budget.getId(),
                budget.getUserId(),
                budget.getMonthlyBudget()
        );
    }

    public static void updateEntity(Budget budget, BudgetIn budgetIn) {
        budget.setMonthlyBudget(budgetIn.getMonthlyBudget());
    }
}