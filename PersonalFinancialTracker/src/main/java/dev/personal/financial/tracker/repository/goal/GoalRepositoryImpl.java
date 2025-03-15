package dev.personal.financial.tracker.repository.goal;

import dev.personal.financial.tracker.dto.goal.GoalMapper;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;
import dev.personal.financial.tracker.model.Goal;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.sql.*;

@RequiredArgsConstructor
public class GoalRepositoryImpl implements GoalRepository {

    private final Connection connection;

    @Override
    public void save(Goal goal) {
        String sql = "INSERT INTO goals (user_id, goal_name, goal_amount, current_amount, saved_amount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, goal.getUserId());
            statement.setString(2, goal.getGoalName());
            statement.setBigDecimal(3, goal.getGoalAmount());
            statement.setBigDecimal(4, goal.getCurrentAmount());
            statement.setBigDecimal(5, goal.getSavedAmount());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    goal.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save goal", e);
        }
    }

    @Override
    public Goal findById(int id) throws GoalNotFoundException {
        String sql = "SELECT * FROM goals WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return GoalMapper.mapRowToGoal(resultSet);
                } else {
                    throw new GoalNotFoundException();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find goal by ID", e);
        }
    }

    @Override
    public Goal findByUserId(int userId) {
        String sql = "SELECT * FROM goals WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return GoalMapper.mapRowToGoal(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find goal by user ID", e);
        }
        return null;
    }

    @Override
    public void update(Goal goal) {
        String sql = "UPDATE goals SET goal_name = ?, goal_amount = ?, current_amount = ?, saved_amount = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, goal.getGoalName());
            statement.setBigDecimal(2, goal.getGoalAmount());
            statement.setBigDecimal(3, goal.getCurrentAmount());
            statement.setBigDecimal(4, goal.getSavedAmount());
            statement.setInt(5, goal.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update goal", e);
        }
    }

    @Override
    public void deleteByUserId(int userId) throws GoalNotFoundException {
        String sql = "DELETE FROM goals WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new GoalNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete goal by user ID", e);
        }
    }

    @Override
    public void updateSavedAmount(int goalId, BigDecimal amount) throws GoalNotFoundException {
        String sql = "UPDATE goals SET saved_amount = saved_amount + ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, amount);
            statement.setInt(2, goalId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new GoalNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update saved amount for goal", e);
        }
    }

    @Override
    public BigDecimal getSavedAmount(int goalId) throws GoalNotFoundException {
        String sql = "SELECT saved_amount FROM goals WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, goalId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal("saved_amount");
                } else {
                    throw new GoalNotFoundException();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get saved amount for goal", e);
        }
    }
}
