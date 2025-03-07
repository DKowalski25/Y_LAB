package dev.personal.financial.tracker.dto.goal;

import dev.personal.financial.tracker.model.Goal;

public class GoalMapper {

    public static Goal toEntity(GoalIn goalIn) {
        return new Goal(
                goalIn.getId(),
                goalIn.getUserId(),
                goalIn.getGoalName(),
                goalIn.getGoalAmount(),
                0.0
        );
    }

    public static GoalOut toDto(Goal goal) {
        return new GoalOut(
                goal.getId(),
                goal.getUserId(),
                goal.getGoalName(),
                goal.getGoalAmount(),
                goal.getCurrentAmount()
        );
    }

    public static void updateEntity(Goal goal, GoalIn goalIn) {
        goal.setGoalName(goalIn.getGoalName());
        goal.setGoalAmount(goal.getGoalAmount());
    }
}