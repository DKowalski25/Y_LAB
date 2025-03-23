package dev.personal.financial.tracker.unit.dto.notification;

import dev.personal.financial.tracker.dto.notification.NotificationIn;
import dev.personal.financial.tracker.dto.notification.NotificationMapper;
import dev.personal.financial.tracker.dto.notification.NotificationOut;
import dev.personal.financial.tracker.model.Notification;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationMapperTest {

    private final static int ID = UUID.randomUUID().hashCode();
    private final static int USER_ID = UUID.randomUUID().hashCode();

    @Test
    void toEntity_ShouldMapNotificationInToNotification() {
        NotificationIn notificationIn = new NotificationIn(USER_ID, "Test message");

        Notification notification = NotificationMapper.toEntity(notificationIn);

        assertThat(notification.getId()).isNotZero();
        assertThat(notification.getUserId()).isEqualTo(USER_ID);
        assertThat(notification.getMessage()).isEqualTo("Test message");
        assertThat(notification.getCreatedAt()).isNotNull();
    }

    @Test
    void toDto_ShouldMapNotificationToNotificationOut() {
        Notification notification = new Notification(
                ID,
                USER_ID,
                "Test message",
                LocalDateTime.now()
        );

        NotificationOut notificationOut = NotificationMapper.toDto(notification);

        assertThat(notificationOut.getId()).isEqualTo(ID);
        assertThat(notificationOut.getUserId()).isEqualTo(USER_ID);
        assertThat(notificationOut.getMessage()).isEqualTo("Test message");
        assertThat(notificationOut.getCreatedAt()).isEqualTo(notification.getCreatedAt());
    }
}