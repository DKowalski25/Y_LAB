package dev.personal.financial.tracker.service.notification;

import dev.personal.financial.tracker.dto.notification.NotificationIn;
import dev.personal.financial.tracker.dto.notification.NotificationOut;
import dev.personal.financial.tracker.model.Notification;
import dev.personal.financial.tracker.repository.notification.NotificationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void sendNotification_ShouldSaveNotification() {
        NotificationIn notificationIn = new NotificationIn(
                "user1",
                "Test message"
        );
        Notification notification = new Notification(
                "user1",
                "Test message"
        );

        doNothing().when(notificationRepository).save(any(Notification.class));

        notificationService.sendNotification(notificationIn);

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void getNotificationsByUserId_ShouldReturnListOfNotificationOut() {
        String userId = "user1";
        Notification notification1 = new Notification(
                UUID.randomUUID().toString(), userId,
                "Message 1",
                LocalDateTime.now()
        );
        Notification notification2 = new Notification(
                UUID.randomUUID().toString(),
                userId,
                "Message 2",
                LocalDateTime.now()
        );

        when(notificationRepository.findByUserId(userId)).thenReturn(List.of(notification1, notification2));

        List<NotificationOut> result = notificationService.getNotificationsByUserId(userId);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUserId()).isEqualTo(userId);
        assertThat(result.get(0).getMessage()).isEqualTo("Message 1");

        assertThat(result.get(1).getUserId()).isEqualTo(userId);
        assertThat(result.get(1).getMessage()).isEqualTo("Message 2");

        verify(notificationRepository, times(1)).findByUserId(userId);
    }

    @Test
    void deleteNotification_ShouldDeleteNotification() {
        String notificationId = UUID.randomUUID().toString();
        doNothing().when(notificationRepository).delete(notificationId);

        notificationService.deleteNotification(notificationId);

        verify(notificationRepository, times(1)).delete(notificationId);
    }

//    @Test
//    void sendEmailNotification_ShouldPrintEmailDetails() {
//
//        String email = "test@example.com";
//        String subject = "Test Subject";
//        String message = "Test Message";
//
//        notificationService.sendEmailNotification(email, subject, message);
//    }
}