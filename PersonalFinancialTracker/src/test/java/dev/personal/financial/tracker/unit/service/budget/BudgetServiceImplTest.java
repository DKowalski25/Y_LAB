package dev.personal.financial.tracker.unit.service.budget;

import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.exception.budget.BudgetNotFoundException;
import dev.personal.financial.tracker.model.Budget;
import dev.personal.financial.tracker.repository.budget.BudgetRepository;

import dev.personal.financial.tracker.service.budget.BudgetServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BudgetServiceImplTest {

    private final static int ID = UUID.randomUUID().hashCode();
    private final static int USER_ID = UUID.randomUUID().hashCode();

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
                ID,
                USER_ID,
                BigDecimal.valueOf(1000.0)
        );
        Budget budget = new Budget(
                ID,
                USER_ID,
                BigDecimal.valueOf(1000.0)
        );

        doNothing().when(budgetRepository).save(any(Budget.class));

        budgetService.setBudget(budgetIn);

        verify(budgetRepository, times(1)).save(any(Budget.class));
    }

    @Test
    void getBudgetByUserId_ShouldReturnBudgetOut_WhenBudgetExists() {
        Budget budget = new Budget(
                ID,
                USER_ID,
                BigDecimal.valueOf(1000.0)
        );
        when(budgetRepository.findByUserId(USER_ID)).thenReturn(budget);

        BudgetOut result = budgetService.getBudgetByUserId(USER_ID);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getMonthlyBudget()).isEqualTo(BigDecimal.valueOf(1000.0));
        verify(budgetRepository, times(1)).findByUserId(USER_ID);
    }

    @Test
    void getBudgetByUserId_ShouldThrowException_WhenBudgetDoesNotExist() {
        when(budgetRepository.findByUserId(USER_ID)).thenThrow(new BudgetNotFoundException(USER_ID));

        assertThatThrownBy(() -> budgetService.getBudgetByUserId(USER_ID))
                .isInstanceOf(BudgetNotFoundException.class)
                .hasMessage("Бюджет для пользователя с id: " + USER_ID + " не найден.");

        verify(budgetRepository, times(1)).findByUserId(USER_ID);
    }

    @Test
    void updateBudget_ShouldUpdateBudget_WhenBudgetExists() {
        BudgetIn budgetIn = new BudgetIn(
                ID,
                USER_ID,
                BigDecimal.valueOf(2000.0)
        );
        Budget existingBudget = new Budget(
                ID,
                USER_ID,
                BigDecimal.valueOf(1000.0)
        );

        when(budgetRepository.findByUserId(USER_ID)).thenReturn(existingBudget);
        doNothing().when(budgetRepository).update(any(Budget.class));

        budgetService.updateBudget(budgetIn);

        verify(budgetRepository, times(1)).findByUserId(USER_ID);
        verify(budgetRepository, times(1)).update(existingBudget);
        assertThat(existingBudget.getMonthlyBudget()).isEqualTo(BigDecimal.valueOf(2000.0));
    }

    @Test
    void updateBudget_ShouldThrowException_WhenBudgetDoesNotExist() {
        BudgetIn budgetIn = new BudgetIn(
                ID,
                USER_ID,
                BigDecimal.valueOf(2000.0));

        when(budgetRepository.findByUserId(USER_ID)).thenThrow(new BudgetNotFoundException(USER_ID));

        assertThatThrownBy(() -> budgetService.updateBudget(budgetIn))
                .isInstanceOf(BudgetNotFoundException.class)
                .hasMessage("Бюджет для пользователя с id: " + USER_ID + " не найден.");

        verify(budgetRepository, times(1)).findByUserId(USER_ID);
        verify(budgetRepository, never()).update(any(Budget.class));
    }

    @Test
    void deleteBudget_ShouldDeleteBudget_WhenBudgetExists() {
        doNothing().when(budgetRepository).delete(USER_ID);

        budgetService.deleteBudget(USER_ID);

        verify(budgetRepository, times(1)).delete(USER_ID);
    }
}