package dev.personal.financial.tracker.service.budget;

import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.model.Budget;
import dev.personal.financial.tracker.repository.budget.BudgetRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BudgetServiceImplTest {

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private BudgetServiceImpl budgetService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void setBudget_ShouldSaveBudget() {
        BudgetIn budgetIn = new BudgetIn(
                "1",
                "user1",
                1000.0
        );
        Budget budget = new Budget(
                "1",
                "user1",
                1000.0
        );

        doNothing().when(budgetRepository).save(any(Budget.class));

        budgetService.setBudget(budgetIn);

        verify(budgetRepository, times(1)).save(any(Budget.class));
    }

    @Test
    void getBudgetByUserId_ShouldReturnBudgetOut_WhenBudgetExists() {
        String userId = "user1";
        Budget budget = new Budget(
                "1",
                userId,
                1000.0
        );
        when(budgetRepository.findByUserId(userId)).thenReturn(budget);

        BudgetOut result = budgetService.getBudgetByUserId(userId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("1");
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getMonthlyBudget()).isEqualTo(1000.0);
        verify(budgetRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getBudgetByUserId_ShouldReturnNull_WhenBudgetDoesNotExist() {
        String userId = "user1";
        when(budgetRepository.findByUserId(userId)).thenReturn(null);

        BudgetOut result = budgetService.getBudgetByUserId(userId);

        assertThat(result).isNull();
        verify(budgetRepository, times(1)).findByUserId(userId);
    }

    @Test
    void updateBudget_ShouldUpdateBudget_WhenBudgetExists() {
        BudgetIn budgetIn = new BudgetIn(
                "1",
                "user1",
                2000.0
        );
        Budget existingBudget = new Budget(
                "1",
                "user1",
                1000.0
        );

        when(budgetRepository.findByUserId("user1")).thenReturn(existingBudget);
        doNothing().when(budgetRepository).update(any(Budget.class));

        budgetService.updateBudget(budgetIn);

        verify(budgetRepository, times(1)).findByUserId("user1");
        verify(budgetRepository, times(1)).update(existingBudget);
        assertThat(existingBudget.getMonthlyBudget()).isEqualTo(2000.0);
    }

    @Test
    void updateBudget_ShouldDoNothing_WhenBudgetDoesNotExist() {
        BudgetIn budgetIn = new BudgetIn("1", "user1", 2000.0);

        when(budgetRepository.findByUserId("user1")).thenReturn(null);

        budgetService.updateBudget(budgetIn);

        verify(budgetRepository, times(1)).findByUserId("user1");
        verify(budgetRepository, never()).update(any(Budget.class));
    }

    @Test
    void deleteBudget_ShouldDeleteBudget_WhenBudgetExists() {
        String userId = "user1";
        doNothing().when(budgetRepository).delete(userId);

        budgetService.deleteBudget(userId);

        verify(budgetRepository, times(1)).delete(userId);
    }
}