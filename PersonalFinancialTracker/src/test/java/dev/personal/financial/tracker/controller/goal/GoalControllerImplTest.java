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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GoalControllerImplTest {

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
                "1",
                "1",
                "Buy a car",
                10000.0
        );
        doNothing().when(goalService).addGoal(goalIn);

        goalController.addGoal(goalIn);

        verify(goalService, times(1)).addGoal(goalIn);
    }

    @Test
    void getGoalsByUserId_Success() {
        GoalOut goalOut = new GoalOut(
                "1",
                "1",
                "Buy a car",
                10000.0,
                5000.0,
                5000.0
        );
        when(goalService.getGoalsByUserId("1")).thenReturn(goalOut);

        GoalOut result = goalController.getGoalsByUserId("1");

        assertEquals(goalOut, result);
    }

    @Test
    void updateGoal_Success() {
        GoalIn goalIn = new GoalIn(
                "1",
                "1",
                "Buy a car",
                15000.0
        );
        doNothing().when(goalService).updateGoal(goalIn);

        goalController.updateGoal(goalIn);

        verify(goalService, times(1)).updateGoal(goalIn);
    }

    @Test
    void deleteGoalByUserId_Success() {
        doNothing().when(goalService).deleteGoalByUserId("1");

        goalController.deleteGoalByUserId("1");

        verify(goalService, times(1)).deleteGoalByUserId("1");
    }

    @Test
    void updateSavedAmount_Success() {
        doNothing().when(goalService).updateSavedAmount("1", 2000.0);

        goalController.updateSavedAmount("1", 2000.0);

        verify(goalService, times(1)).updateSavedAmount("1", 2000.0);
    }

    @Test
    void getProgress_Success() {
        when(goalService.getProgress("1")).thenReturn(50.0);

        double result = goalController.getProgress("1");

        assertEquals(50.0, result);
    }
}