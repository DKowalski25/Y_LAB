package dev.personal.financial.tracker.dto.goal;

import dev.personal.financial.tracker.model.Goal;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GoalMapperTest {

    @Test
    void toEntity_ShouldMapGoalInToGoal() {
        GoalIn goalIn = new GoalIn(
                "1",
                "user1",
                "Buy a car",
                10000.0
        );

        Goal goal = GoalMapper.toEntity(goalIn);

        assertThat(goal.getId()).isEqualTo("1");
        assertThat(goal.getUserId()).isEqualTo("user1");
        assertThat(goal.getGoalName()).isEqualTo("Buy a car");
        assertThat(goal.getGoalAmount()).isEqualTo(10000.0);
        assertThat(goal.getCurrentAmount()).isEqualTo(0.0);
        assertThat(goal.getSavedAmount()).isEqualTo(0.0);
    }

    @Test
    void toDto_ShouldMapGoalToGoalOut() {
        Goal goal = new Goal(
                "1",
                "user1",
                "Buy a car",
                10000.0,
                5000.0,
                5000.0
        );

        GoalOut goalOut = GoalMapper.toDto(goal);

        assertThat(goalOut.getId()).isEqualTo("1");
        assertThat(goalOut.getUserId()).isEqualTo("user1");
        assertThat(goalOut.getGoalName()).isEqualTo("Buy a car");
        assertThat(goalOut.getGoalAmount()).isEqualTo(10000.0);
        assertThat(goalOut.getCurrentAmount()).isEqualTo(5000.0);
        assertThat(goalOut.getSavedAmount()).isEqualTo(5000.0);
    }

    @Test
    void updateEntity_ShouldUpdateGoalFields() {
        Goal goal = new Goal(
                "1",
                "user1",
                "Buy a car",
                10000.0,
                5000.0,
                5000.0
        );
        GoalIn goalIn = new GoalIn(
                "1",
                "user1",
                "Buy a house",
                200000.0
        );

        GoalMapper.updateEntity(goal, goalIn);

        assertThat(goal.getGoalName()).isEqualTo("Buy a house");
        assertThat(goal.getGoalAmount()).isEqualTo(200000.0);
    }
}