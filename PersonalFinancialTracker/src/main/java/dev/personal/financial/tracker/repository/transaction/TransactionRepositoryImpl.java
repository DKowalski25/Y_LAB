package dev.personal.financial.tracker.repository.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionMapper;
import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;
import dev.personal.financial.tracker.model.Transaction;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final Connection connection;

    @Override
    public void save(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, amount, category, date, description, is_income)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, transaction.getUserId());
            statement.setBigDecimal(2, transaction.getAmount());
            statement.setString(3, transaction.getCategory());
            statement.setDate(4, java.sql.Date.valueOf(transaction.getDate()));
            statement.setString(5, transaction.getDescription());
            statement.setBoolean(6, transaction.isIncome());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaction.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save transaction", e);
        }
    }

    @Override
    public List<Transaction> findByUserId(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(TransactionMapper.mapRowToTransaction(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transactions from the database", e);
        }
        return transactions;
    }

    @Override
    public Transaction findById(int id) throws TransactionNotFoundException {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return TransactionMapper.mapRowToTransaction(resultSet);
                } else {
                    throw new TransactionNotFoundException(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transaction from the database", e);
        }
    }

    @Override
    public void update(Transaction transaction) {
        String sql = "UPDATE transactions SET amount = ?, category = ?, date = ?, description = ?, is_income = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, transaction.getAmount());
            statement.setString(2, transaction.getCategory());
            statement.setDate(3, Date.valueOf(transaction.getDate()));
            statement.setString(4, transaction.getDescription());
            statement.setBoolean(5, transaction.isIncome());
            statement.setInt(6, transaction.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update transaction", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete transaction", e);
        }

    }

    @Override
    public List<Transaction> findByUserIdAndCategory(int userId, String category) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? AND category = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setString(2, category);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(TransactionMapper.mapRowToTransaction(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transactions from the database", e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> findByUserIdAndDate(int userId, LocalDate date) {
        List<Transaction> transactions = new ArrayList<>();
        String sql ="SELECT * FROM transactions WHERE user_id = ? AND date = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setDate(2, Date.valueOf(date));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(TransactionMapper.mapRowToTransaction(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transactions from the database", e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> findByUserIdAndType(int userId, boolean isIncome) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? AND is_income = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setBoolean(2, isIncome);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(TransactionMapper.mapRowToTransaction(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transactions from the database", e);
        }
        return transactions;
    }

    @Override
    public BigDecimal getTotalExpensesForCurrentMonth(int userId) {
        String sql = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND is_income = false AND date >= ? AND date <= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            LocalDate now = LocalDate.now();
            LocalDate startOfMonth = now.withDayOfMonth(1);
            LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

            statement.setInt(1, userId);
            statement.setDate(2, Date.valueOf(startOfMonth));
            statement.setDate(3, Date.valueOf(endOfMonth));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get total expenses for current month", e);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public List<Transaction> findByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? AND date >= ? AND date <= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setDate(2, Date.valueOf(startDate));
            statement.setDate(3, Date.valueOf(endDate));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(TransactionMapper.mapRowToTransaction(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find transactions by user ID and date range", e);
        }
        return transactions;
    }
}
