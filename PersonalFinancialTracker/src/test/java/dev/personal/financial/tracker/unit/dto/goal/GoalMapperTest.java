package dev.personal.financial.tracker.unit.dto.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalMapper;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.model.Goal;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GoalMapperTest {

    private final static int ID = UUID.randomUUID().hashCode();
    private final static int USER_ID = UUID.randomUUID().hashCode();

    @Test
    void toEntity_ShouldMapGoalInToGoal() {
        GoalIn goalIn = new GoalIn(
                ID,
                USER_ID,
                "Buy a car",
                BigDecimal.valueOf(10000.0)
        );

        Goal goal = GoalMapper.toEntity(goalIn);

        assertThat(goal.getId()).isEqualTo(ID);
        assertThat(goal.getUserId()).isEqualTo(USER_ID);
        assertThat(goal.getGoalName()).isEqualTo("Buy a car");
        assertThat(goal.getGoalAmount()).isEqualTo(BigDecimal.valueOf(10000.0));
        assertThat(goal.getCurrentAmount()).isEqualTo(BigDecimal.valueOf(0.0));
        assertThat(goal.getSavedAmount()).isEqualTo(BigDecimal.valueOf(0.0));
    }

    @Test
    void toDto_ShouldMapGoalToGoalOut() {
        Goal goal = new Goal(
                ID,
                USER_ID,
                "Buy a car",
                BigDecimal.valueOf(10000.0),
                BigDecimal.valueOf(5000.0),
                BigDecimal.valueOf(5000.0)
        );

        GoalOut goalOut = GoalMapper.toDto(goal);

        assertThat(goalOut.getId()).isEqualTo(ID);
        assertThat(goalOut.getUserId()).isEqualTo(USER_ID);
        assertThat(goalOut.getGoalName()).isEqualTo("Buy a car");
        assertThat(goalOut.getGoalAmount()).isEqualTo(BigDecimal.valueOf(10000.0));
        assertThat(goalOut.getCurrentAmount()).isEqualTo(BigDecimal.valueOf(5000.0));
        assertThat(goalOut.getSavedAmount()).isEqualTo(BigDecimal.valueOf(5000.0));
    }

    @Test
    void updateEntity_ShouldUpdateGoalFields() {
        Goal goal = new Goal(
                ID,
                USER_ID,
                "Buy a car",
                BigDecimal.valueOf(10000.0),
                BigDecimal.valueOf(5000.0),
                BigDecimal.valueOf(5000.0)
        );
        GoalIn goalIn = new GoalIn(
                ID,
                USER_ID,
                "Buy a house",
                BigDecimal.valueOf(200000.0)
        );

        GoalMapper.updateEntity(goal, goalIn);

        assertThat(goal.getGoalName()).isEqualTo("Buy a house");
        assertThat(goal.getGoalAmount()).isEqualTo(BigDecimal.valueOf(200000.0));
    }
}