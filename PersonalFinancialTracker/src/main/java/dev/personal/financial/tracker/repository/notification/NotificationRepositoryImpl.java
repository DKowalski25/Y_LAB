package dev.personal.financial.tracker.repository.notification;

import dev.personal.financial.tracker.dto.notification.NotificationMapper;
import dev.personal.financial.tracker.model.Notification;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final Connection connection;

    @Override
    public void save(Notification notification) {
        String sql = "INSERT INTO notifications (user_id, message, date) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, notification.getId());
            statement.setString(2, notification.getMessage());
            statement.setDate(3, Date.valueOf(String.valueOf(notification.getCreatedAt())));

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    notification.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save notification", e);
        }
    }

    @Override
    public List<Notification> findByUserId(int userId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    notifications.add(NotificationMapper.mapRowToNotification(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve notifications from the database", e);
        }
        return notifications;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM notifications WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete notification", e);
        }
    }
}
