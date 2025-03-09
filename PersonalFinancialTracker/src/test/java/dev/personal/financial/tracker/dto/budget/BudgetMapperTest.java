package dev.personal.financial.tracker.dto.budget;

import dev.personal.financial.tracker.model.Budget;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BudgetMapperTest {

    @Test
    void toEntity_ShouldMapBudgetInToBudget() {
        BudgetIn budgetIn = new BudgetIn(
                "1",
                "user1",
                1000.0
        );

        Budget budget = BudgetMapper.toEntity(budgetIn);

        assertThat(budget.getId()).isEqualTo("1");
        assertThat(budget.getUserId()).isEqualTo("user1");
        assertThat(budget.getMonthlyBudget()).isEqualTo(1000.0);
    }

    @Test
    void toDto_ShouldMapBudgetToBudgetOut() {
        Budget budget = new Budget(
                "1",
                "user1",
                1000.0
        );

        BudgetOut budgetOut = BudgetMapper.toDto(budget);

        assertThat(budgetOut.getId()).isEqualTo("1");
        assertThat(budgetOut.getUserId()).isEqualTo("user1");
        assertThat(budgetOut.getMonthlyBudget()).isEqualTo(1000.0);
    }

    @Test
    void updateEntity_ShouldUpdateBudgetFields() {
        Budget budget = new Budget("1", "user1", 1000.0);
        BudgetIn budgetIn = new BudgetIn("1", "user1", 2000.0);

        BudgetMapper.updateEntity(budget, budgetIn);

        assertThat(budget.getMonthlyBudget()).isEqualTo(2000.0);
    }
}