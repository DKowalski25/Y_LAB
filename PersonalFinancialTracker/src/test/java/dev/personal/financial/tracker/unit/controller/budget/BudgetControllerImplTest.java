package dev.personal.financial.tracker.unit.controller.budget;

import dev.personal.financial.tracker.controller.budget.BudgetControllerImpl;
import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.service.budget.BudgetService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BudgetControllerImplTest {

    private final static int ID = UUID.randomUUID().hashCode();
    private final static int USER_ID = UUID.randomUUID().hashCode();

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetControllerImpl budgetController;

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
    void setBudget_Success() {
        BudgetIn budgetIn = new BudgetIn(
                ID,
                USER_ID,
                BigDecimal.valueOf(2000.0));
        doNothing().when(budgetService).setBudget(budgetIn);

        budgetController.setBudget(budgetIn);

        verify(budgetService, times(1)).setBudget(budgetIn);
    }

    @Test
    void getBudgetByUserId_Success() {
        BudgetOut budgetOut = new BudgetOut(
                ID,
                USER_ID,
                BigDecimal.valueOf(2000.0)
        );
        when(budgetService.getBudgetByUserId(USER_ID)).thenReturn(budgetOut);

        BudgetOut result = budgetController.getBudgetByUserId(USER_ID);

        assertEquals(budgetOut, result);
    }

    @Test
    void updateBudget_Success() {
        BudgetIn budgetIn = new BudgetIn(
                ID,
                USER_ID,
                BigDecimal.valueOf(2500.0)
        );
        doNothing().when(budgetService).updateBudget(budgetIn);

        budgetController.updateBudget(budgetIn);

        verify(budgetService, times(1)).updateBudget(budgetIn);
    }

    @Test
    void deleteBudget_Success() {
        doNothing().when(budgetService).deleteBudget(USER_ID);

        budgetController.deleteBudget(USER_ID);

        verify(budgetService, times(1)).deleteBudget(USER_ID);
    }
}