package dev.personal.financial.tracker.unit.dto.budget;

import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetMapper;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.model.Budget;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BudgetMapperTest {

    private final static int ID = UUID.randomUUID().hashCode();
    private final static int USER_ID = UUID.randomUUID().hashCode();

    @Test
    void toEntity_ShouldMapBudgetInToBudget() {
        BudgetIn budgetIn = new BudgetIn(
                ID,
                USER_ID,
                BigDecimal.valueOf(1000.0)
        );

        Budget budget = BudgetMapper.toEntity(budgetIn);

        assertThat(budget.getId()).isEqualTo(ID);
        assertThat(budget.getUserId()).isEqualTo(USER_ID);
        assertThat(budget.getMonthlyBudget()).isEqualTo(BigDecimal.valueOf(1000.0));
    }

    @Test
    void toDto_ShouldMapBudgetToBudgetOut() {
        Budget budget = new Budget(
                ID,
                USER_ID,
                BigDecimal.valueOf(1000.0)
        );

        BudgetOut budgetOut = BudgetMapper.toDto(budget);

        assertThat(budgetOut.getId()).isEqualTo(ID);
        assertThat(budgetOut.getUserId()).isEqualTo(USER_ID);
        assertThat(budgetOut.getMonthlyBudget()).isEqualTo(BigDecimal.valueOf(1000.0));
    }

    @Test
    void updateEntity_ShouldUpdateBudgetFields() {
        Budget budget = new Budget(
                ID,
                USER_ID,
                BigDecimal.valueOf(1000.0)
        );
        BudgetIn budgetIn = new BudgetIn(
                ID,
                USER_ID,
                BigDecimal.valueOf(2000.0)
        );

        BudgetMapper.updateEntity(budget, budgetIn);

        assertThat(budget.getMonthlyBudget()).isEqualTo(BigDecimal.valueOf(2000.0));
    }
}