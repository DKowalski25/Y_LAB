package dev.personal.financial.tracker.controller.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.service.goal.GoalService;

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

class GoalControllerImplTest {

    private final static int ID = UUID.randomUUID().hashCode();
    private final static int USER_ID = UUID.randomUUID().hashCode();

    @Mock
    private GoalService goalService;

    @InjectMocks
    private GoalControllerImpl goalController;

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
    void addGoal_Success() {
        GoalIn goalIn = new GoalIn(
                ID,
                USER_ID,
                "Buy a car",
                BigDecimal.valueOf(10000.0)
        );
        doNothing().when(goalService).addGoal(goalIn);

        goalController.addGoal(goalIn);

        verify(goalService, times(1)).addGoal(goalIn);
    }

    @Test
    void getGoalsByUserId_Success() {
        GoalOut goalOut = new GoalOut(
                ID,
                USER_ID,
                "Buy a car",
                BigDecimal.valueOf(10000.0),
                BigDecimal.valueOf(5000.0),
                BigDecimal.valueOf(5000.0)
        );
        when(goalService.getGoalsByUserId(USER_ID)).thenReturn(goalOut);

        GoalOut result = goalController.getGoalsByUserId(USER_ID);

        assertEquals(goalOut, result);
    }

    @Test
    void updateGoal_Success() {
        GoalIn goalIn = new GoalIn(
                ID,
                USER_ID,
                "Buy a car",
                BigDecimal.valueOf(15000.0)
        );
        doNothing().when(goalService).updateGoal(goalIn);

        goalController.updateGoal(goalIn);

        verify(goalService, times(1)).updateGoal(goalIn);
    }

    @Test
    void deleteGoalByUserId_Success() {
        doNothing().when(goalService).deleteGoalByUserId(USER_ID);

        goalController.deleteGoalByUserId(USER_ID);

        verify(goalService, times(1)).deleteGoalByUserId(USER_ID);
    }

    @Test
    void updateSavedAmount_Success() {
        doNothing().when(goalService).updateSavedAmount(ID, BigDecimal.valueOf(2000.0));

        goalController.updateSavedAmount(ID, BigDecimal.valueOf(2000.0));

        verify(goalService, times(1)).updateSavedAmount(ID, BigDecimal.valueOf(2000.0));
    }

    @Test
    void getProgress_Success() {
        when(goalService.getProgress(ID)).thenReturn(50.0);

        double result = goalController.getProgress(ID);

        assertEquals(50.0, result);
    }
}