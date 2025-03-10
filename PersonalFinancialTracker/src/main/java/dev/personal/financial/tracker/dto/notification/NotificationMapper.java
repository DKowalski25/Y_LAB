package dev.personal.financial.tracker.dto.notification;

import dev.personal.financial.tracker.model.Notification;

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
}