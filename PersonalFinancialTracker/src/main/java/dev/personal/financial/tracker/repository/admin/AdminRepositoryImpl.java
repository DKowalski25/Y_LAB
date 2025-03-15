package dev.personal.financial.tracker.repository.admin;

import dev.personal.financial.tracker.dto.user.UserMapper;
import dev.personal.financial.tracker.exception.user.UserAlreadyBlockedException;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.model.User;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {

    private final Connection connection;

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(UserMapper.mapRowToUser(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all users", e);
        }
        return users;
    }

    @Override
    public void blockUser(int userId) throws UserNotFoundException, UserAlreadyBlockedException {
        String sql = "UPDATE users SET is_blocked = true WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new UserNotFoundException(userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to block user", e);
        }
    }

    @Override
    public void deleteUser(int userId) throws UserNotFoundException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new UserNotFoundException("User with ID " + userId + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }
}
