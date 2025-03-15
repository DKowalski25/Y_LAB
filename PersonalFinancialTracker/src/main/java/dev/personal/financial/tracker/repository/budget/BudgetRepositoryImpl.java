package dev.personal.financial.tracker.repository.budget;

import dev.personal.financial.tracker.dto.budget.BudgetMapper;
import dev.personal.financial.tracker.exception.budget.BudgetNotFoundException;
import dev.personal.financial.tracker.model.Budget;
import lombok.RequiredArgsConstructor;

import java.sql.*;

@RequiredArgsConstructor
public class BudgetRepositoryImpl implements BudgetRepository {

    private final Connection connection;

    @Override
    public void save(Budget budget){
        String sql = "INSERT INTO budgets (user_id, monthly_budget) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, budget.getUserId());
            statement.setBigDecimal(2, budget.getMonthlyBudget());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    budget.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save budget", e);
        }
    }

    @Override
    public Budget findByUserId(int userId) throws BudgetNotFoundException {
        String sql = "SELECT * FROM budgets WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return BudgetMapper.mapRowToBudget(resultSet);
                } else {
                    throw new BudgetNotFoundException(userId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find budget by user ID", e);
        }
    }

    @Override
    public void update(Budget budget) {
        String sql = "UPDATE budgets SET monthly_budget = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, budget.getMonthlyBudget());
            statement.setInt(2, budget.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update budget", e);
        }
    }

    @Override
    public void delete(int userId) {
        String sql = "DELETE FROM budgets WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete budget", e);
        }
    }
}
