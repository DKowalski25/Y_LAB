package dev.personal.financial.tracker.dto.notification;

import dev.personal.financial.tracker.model.Notification;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationMapperTest {

    @Test
    void toEntity_ShouldMapNotificationInToNotification() {
        NotificationIn notificationIn = new NotificationIn("user1", "Test message");

        Notification notification = NotificationMapper.toEntity(notificationIn);

        assertThat(notification.getId()).isNotNull();
        assertThat(notification.getUserId()).isEqualTo("user1");
        assertThat(notification.getMessage()).isEqualTo("Test message");
        assertThat(notification.getCreatedAt()).isNotNull();
    }

    @Test
    void toDto_ShouldMapNotificationToNotificationOut() {
        Notification notification = new Notification(
                UUID.randomUUID().toString(),
                "user1",
                "Test message",
                LocalDateTime.now()
        );

        NotificationOut notificationOut = NotificationMapper.toDto(notification);

        assertThat(notificationOut.getId()).isEqualTo(notification.getId());
        assertThat(notificationOut.getUserId()).isEqualTo("user1");
        assertThat(notificationOut.getMessage()).isEqualTo("Test message");
        assertThat(notificationOut.getCreatedAt()).isEqualTo(notification.getCreatedAt());
    }
}