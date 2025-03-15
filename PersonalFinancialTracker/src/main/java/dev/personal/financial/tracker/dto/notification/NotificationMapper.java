package dev.personal.financial.tracker.dto.notification;

import dev.personal.financial.tracker.model.Notification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationMapper {

    public static Notification toEntity(NotificationIn notificationIn) {
        return new Notification(
                UUID.randomUUID().hashCode(),
                notificationIn.getUserId(),
                notificationIn.getMessage(),
                LocalDateTime.now()
        );
    }

    public static NotificationOut toDto (Notification notification) {
        return new NotificationOut(
                notification.getId(),
                notification.getUserId(),
                notification.getMessage(),
                notification.getCreatedAt()
        );
    }

    public static Notification mapRowToNotification(ResultSet resultSet) throws SQLException {
        return new Notification(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("message"),
                resultSet.getTimestamp("created_at").toLocalDateTime()
        );
    }
}