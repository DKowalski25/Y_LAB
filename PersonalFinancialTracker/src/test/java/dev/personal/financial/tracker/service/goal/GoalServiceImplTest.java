package dev.personal.financial.tracker.service.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.model.Goal;
import dev.personal.financial.tracker.repository.goal.GoalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GoalServiceImplTest {

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private GoalServiceImpl goalService;

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
    void addGoal_ShouldSaveGoal() {
        GoalIn goalIn = new GoalIn(
                "1",
                "user1",
                "Buy a car",
                10000.0
        );
        Goal goal = new Goal(
                "1",
                "user1",
                "Buy a car",
                10000.0,
                0.0,
                0.0
        );

        doNothing().when(goalRepository).save(any(Goal.class));

        goalService.addGoal(goalIn);

        verify(goalRepository, times(1)).save(any(Goal.class));
    }

    @Test
    void getGoalById_ShouldReturnGoalOut_WhenGoalExists() {
        String goalId = "1";
        Goal goal = new Goal(
                goalId,
                "user1",
                "Buy a car",
                10000.0,
                5000.0,
                5000.0
        );
        when(goalRepository.findById(goalId)).thenReturn(goal);

        GoalOut result = goalService.getGoalById(goalId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(goalId);
        assertThat(result.getUserId()).isEqualTo("user1");
        assertThat(result.getGoalName()).isEqualTo("Buy a car");
        assertThat(result.getGoalAmount()).isEqualTo(10000.0);
        assertThat(result.getCurrentAmount()).isEqualTo(5000.0);
        assertThat(result.getSavedAmount()).isEqualTo(5000.0);
        verify(goalRepository, times(1)).findById(goalId);
    }

    @Test
    void getGoalById_ShouldReturnNull_WhenGoalDoesNotExist() {
        String goalId = "1";
        when(goalRepository.findById(goalId)).thenReturn(null);

        GoalOut result = goalService.getGoalById(goalId);

        assertThat(result).isNull();
        verify(goalRepository, times(1)).findById(goalId);
    }

    @Test
    void getGoalsByUserId_ShouldReturnGoalOut_WhenGoalExists() {
        String userId = "user1";
        Goal goal = new Goal(
                "1",
                userId,
                "Buy a car",
                10000.0,
                5000.0,
                5000.0
        );
        when(goalRepository.findByUserId(userId)).thenReturn(goal);

        GoalOut result = goalService.getGoalsByUserId(userId);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getGoalName()).isEqualTo("Buy a car");
        assertThat(result.getGoalAmount()).isEqualTo(10000.0);
        assertThat(result.getCurrentAmount()).isEqualTo(5000.0);
        assertThat(result.getSavedAmount()).isEqualTo(5000.0);
        verify(goalRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getGoalsByUserId_ShouldReturnNull_WhenGoalDoesNotExist() {
        String userId = "user1";
        when(goalRepository.findByUserId(userId)).thenReturn(null);

        GoalOut result = goalService.getGoalsByUserId(userId);

        assertThat(result).isNull();
        verify(goalRepository, times(1)).findByUserId(userId);
    }

    @Test
    void updateGoal_ShouldUpdateGoal_WhenGoalExists() {
        GoalIn goalIn = new GoalIn(
                "1",
                "user1",
                "Buy a house",
                200000.0
        );
        Goal existingGoal = new Goal(
                "1",
                "user1",
                "Buy a car",
                10000.0,
                5000.0,
                5000.0
        );

        when(goalRepository.findById("1")).thenReturn(existingGoal);
        doNothing().when(goalRepository).update(any(Goal.class));

        goalService.updateGoal(goalIn);

        verify(goalRepository, times(1)).findById("1");
        verify(goalRepository, times(1)).update(existingGoal);
        assertThat(existingGoal.getGoalName()).isEqualTo("Buy a house");
        assertThat(existingGoal.getGoalAmount()).isEqualTo(200000.0);
    }

    @Test
    void updateGoal_ShouldDoNothing_WhenGoalDoesNotExist() {
        GoalIn goalIn = new GoalIn(
                "1",
                "user1",
                "Buy a house",
                200000.0
        );

        when(goalRepository.findById("1")).thenReturn(null);

        goalService.updateGoal(goalIn);

        verify(goalRepository, times(1)).findById("1");
        verify(goalRepository, never()).update(any(Goal.class));
    }

    @Test
    void deleteGoalByUserId_ShouldDeleteGoal_WhenGoalExists() {
        String userId = "user1";
        doNothing().when(goalRepository).deleteByUserId(userId);

        goalService.deleteGoalByUserId(userId);

        verify(goalRepository, times(1)).deleteByUserId(userId);
    }

    @Test
    void updateSavedAmount_ShouldUpdateSavedAmount_WhenGoalExists() {
        String userId = "user1";
        double amount = 1000.0;
        doNothing().when(goalRepository).updateSavedAmount(userId, amount);

        goalService.updateSavedAmount(userId, amount);

        verify(goalRepository, times(1)).updateSavedAmount(userId, amount);
    }

    @Test
    void getProgress_ShouldReturnProgress_WhenGoalExists() {
        String userId = "user1";
        Goal goal = new Goal(
                "1",
                userId,
                "Buy a car",
                10000.0,
                5000.0,
                5000.0
        );
        when(goalRepository.findById(userId)).thenReturn(goal);

        double result = goalService.getProgress(userId);

        assertThat(result).isEqualTo(50.0);
        verify(goalRepository, times(1)).findById(userId);
    }

    @Test
    void getProgress_ShouldReturnZero_WhenGoalDoesNotExist() {
        String userId = "user1";
        when(goalRepository.findById(userId)).thenReturn(null);

        double result = goalService.getProgress(userId);

        assertThat(result).isEqualTo(0.0);
        verify(goalRepository, times(1)).findById(userId);
    }
}