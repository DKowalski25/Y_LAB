package dev.personal.financial.tracker.service.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;
import dev.personal.financial.tracker.model.Goal;
import dev.personal.financial.tracker.repository.goal.GoalRepository;

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

class GoalServiceImplTest {

    private final static int ID = UUID.randomUUID().hashCode();
    private final static int USER_ID = UUID.randomUUID().hashCode();

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
                ID,
                USER_ID,
                "Buy a car",
                BigDecimal.valueOf(10000.0)
        );
        Goal goal = new Goal(
                ID,
                USER_ID,
                "Buy a car",
                BigDecimal.valueOf(10000.0),
                BigDecimal.valueOf(0.0),
                BigDecimal.valueOf(0.0)
        );

        doNothing().when(goalRepository).save(any(Goal.class));

        goalService.addGoal(goalIn);

        verify(goalRepository, times(1)).save(any(Goal.class));
    }

    @Test
    void getGoalById_ShouldReturnGoalOut_WhenGoalExists() {
        Goal goal = new Goal(
                ID,
                USER_ID,
                "Buy a car",
                BigDecimal.valueOf(10000.0),
                BigDecimal.valueOf(5000.0),
                BigDecimal.valueOf(5000.0)
        );
        when(goalRepository.findById(ID)).thenReturn(goal);

        GoalOut result = goalService.getGoalById(ID);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getGoalName()).isEqualTo("Buy a car");
        assertThat(result.getGoalAmount()).isEqualTo(BigDecimal.valueOf(10000.0));
        assertThat(result.getCurrentAmount()).isEqualTo(BigDecimal.valueOf(5000.0));
        assertThat(result.getSavedAmount()).isEqualTo(BigDecimal.valueOf(5000.0));
        verify(goalRepository, times(1)).findById(ID);
    }

    @Test
    void getGoalById_ShouldThrowException_WhenGoalDoesNotExist() {
        when(goalRepository.findById(ID)).thenThrow(new GoalNotFoundException());

        assertThatThrownBy(() -> goalService.getGoalById(ID))
                .isInstanceOf(GoalNotFoundException.class)
                .hasMessage("Цель не найдена.");

        verify(goalRepository, times(1)).findById(ID);
    }

    @Test
    void getGoalsByUserId_ShouldReturnGoalOut_WhenGoalExists() {
        Goal goal = new Goal(
                ID,
                USER_ID,
                "Buy a car",
                BigDecimal.valueOf(10000.0),
                BigDecimal.valueOf(5000.0),
                BigDecimal.valueOf(5000.0)
        );
        when(goalRepository.findByUserId(USER_ID)).thenReturn(goal);

        GoalOut result = goalService.getGoalsByUserId(USER_ID);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getGoalName()).isEqualTo("Buy a car");
        assertThat(result.getGoalAmount()).isEqualTo(BigDecimal.valueOf(10000.0));
        assertThat(result.getCurrentAmount()).isEqualTo(BigDecimal.valueOf(5000.0));
        assertThat(result.getSavedAmount()).isEqualTo(BigDecimal.valueOf(5000.0));
        verify(goalRepository, times(1)).findByUserId(USER_ID);
    }

    @Test
    void getGoalsByUserId_ShouldThrowException_WhenGoalDoesNotExist() {
        when(goalRepository.findByUserId(USER_ID)).thenThrow(new GoalNotFoundException());

        assertThatThrownBy(() -> goalService.getGoalsByUserId(USER_ID))
                .isInstanceOf(GoalNotFoundException.class)
                .hasMessage("Цель не найдена.");

        verify(goalRepository, times(1)).findByUserId(USER_ID);
    }

    @Test
    void updateGoal_ShouldUpdateGoal_WhenGoalExists() {
        GoalIn goalIn = new GoalIn(
                ID,
                USER_ID,
                "Buy a house",
                BigDecimal.valueOf(200000.0)
        );
        Goal existingGoal = new Goal(
                ID,
                USER_ID,
                "Buy a car",
                BigDecimal.valueOf(10000.0),
                BigDecimal.valueOf(5000.0),
                BigDecimal.valueOf(5000.0)
        );

        when(goalRepository.findById(ID)).thenReturn(existingGoal);
        doNothing().when(goalRepository).update(any(Goal.class));

        goalService.updateGoal(goalIn);

        verify(goalRepository, times(1)).findById(ID);
        verify(goalRepository, times(1)).update(existingGoal);
        assertThat(existingGoal.getGoalName()).isEqualTo("Buy a house");
        assertThat(existingGoal.getGoalAmount()).isEqualTo(BigDecimal.valueOf(200000.0));
    }

    @Test
    void updateGoal_ShouldThrowException_WhenGoalDoesNotExist() {
        GoalIn goalIn = new GoalIn(
                ID,
                USER_ID,
                "Buy a house",
                BigDecimal.valueOf(200000.0)
        );

        when(goalRepository.findById(ID)).thenThrow(new GoalNotFoundException());

        assertThatThrownBy(() -> goalService.updateGoal(goalIn))
                .isInstanceOf(GoalNotFoundException.class)
                .hasMessage("Цель не найдена.");

        verify(goalRepository, times(1)).findById(ID);
        verify(goalRepository, never()).update(any(Goal.class));
    }

    @Test
    void deleteGoalByUserId_ShouldDeleteGoal_WhenGoalExists() {
        Goal goal = new Goal(
                ID,
                USER_ID,
                "Buy a car",
                BigDecimal.valueOf(10000),
                BigDecimal.valueOf(5000),
                BigDecimal.valueOf(5000)
        );
        when(goalRepository.findByUserId(USER_ID)).thenReturn(goal);

        doNothing().when(goalRepository).deleteByUserId(USER_ID);

        goalService.deleteGoalByUserId(USER_ID);

        verify(goalRepository, times(1)).deleteByUserId(USER_ID);
    }

    @Test
    void updateSavedAmount_ShouldUpdateSavedAmount_WhenGoalExists() {
        BigDecimal amount = BigDecimal.valueOf(1000.0);
        doNothing().when(goalRepository).updateSavedAmount(USER_ID, amount);

        goalService.updateSavedAmount(USER_ID, amount);

        verify(goalRepository, times(1)).updateSavedAmount(USER_ID, amount);
    }

    @Test
    void getProgress_ShouldReturnProgress_WhenGoalExists() {
        Goal goal = new Goal(
                ID,
                USER_ID,
                "Buy a car",
                BigDecimal.valueOf(10000.0),
                BigDecimal.valueOf(5000.0),
                BigDecimal.valueOf(5000.0)
        );
        when(goalRepository.findById(USER_ID)).thenReturn(goal);

        double result = goalService.getProgress(USER_ID);

        assertThat(result).isEqualTo(50.0);
        verify(goalRepository, times(1)).findById(USER_ID);
    }

    @Test
    void getProgress_ShouldThrowException_WhenGoalDoesNotExist() {

        when(goalRepository.findById(ID)).thenThrow(new GoalNotFoundException());

        assertThatThrownBy(() -> goalService.getProgress(ID))
                .isInstanceOf(GoalNotFoundException.class)
                .hasMessage("Цель не найдена.");

        verify(goalRepository, times(1)).findById(ID);
    }
}