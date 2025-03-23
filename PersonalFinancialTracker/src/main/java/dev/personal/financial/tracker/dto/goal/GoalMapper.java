package dev.personal.financial.tracker.dto.goal;

import dev.personal.financial.tracker.model.Goal;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoalMapper {

    public static Goal toEntity(GoalIn goalIn) {
        return new Goal(
                goalIn.getId(),
                goalIn.getUserId(),
                goalIn.getGoalName(),
                goalIn.getGoalAmount(),
                BigDecimal.valueOf(0.0),
                BigDecimal.valueOf(0.0)
        );
    }

    public static GoalOut toDto(Goal goal) {
        return new GoalOut(
                goal.getId(),
                goal.getUserId(),
                goal.getGoalName(),
                goal.getGoalAmount(),
                goal.getCurrentAmount(),
                goal.getSavedAmount()
        );
    }

    public static void updateEntity(Goal goal, GoalIn goalIn) {
        goal.setGoalName(goalIn.getGoalName());
        goal.setGoalAmount(goalIn.getGoalAmount());
    }

    public static Goal mapRowToGoal(ResultSet resultSet) throws SQLException {
        return new Goal(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("goal_name"),
                resultSet.getBigDecimal("goal_amount"),
                resultSet.getBigDecimal("current_amount"),
                resultSet.getBigDecimal("saved_amount")
        );
    }
}