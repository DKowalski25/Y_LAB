package dev.personal.financial.tracker.unit.service.notification;

import dev.personal.financial.tracker.dto.notification.NotificationIn;
import dev.personal.financial.tracker.dto.notification.NotificationOut;
import dev.personal.financial.tracker.model.Notification;
import dev.personal.financial.tracker.repository.notification.NotificationRepository;

import dev.personal.financial.tracker.service.notification.NotificationServiceImpl;
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

    private final static int ID = UUID.randomUUID().hashCode();
    private final static int USER_ID = UUID.randomUUID().hashCode();

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
                USER_ID,
                "Test message"
        );
        Notification notification = new Notification(
                USER_ID,
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
                ID,
                USER_ID,
                "Message 1",
                LocalDateTime.now()
        );
        Notification notification2 = new Notification(
                ID,
                USER_ID,
                "Message 2",
                LocalDateTime.now()
        );

        when(notificationRepository.findByUserId(USER_ID)).thenReturn(List.of(notification1, notification2));

        List<NotificationOut> result = notificationService.getNotificationsByUserId(USER_ID);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUserId()).isEqualTo(USER_ID);
        assertThat(result.get(0).getMessage()).isEqualTo("Message 1");

        assertThat(result.get(1).getUserId()).isEqualTo(USER_ID);
        assertThat(result.get(1).getMessage()).isEqualTo("Message 2");

        verify(notificationRepository, times(1)).findByUserId(USER_ID);
    }

    @Test
    void deleteNotification_ShouldDeleteNotification() {
        doNothing().when(notificationRepository).delete(ID);

        notificationService.deleteNotification(ID);

        verify(notificationRepository, times(1)).delete(ID);
    }
}


//    @Test
//    void sendEmailNotification_ShouldPrintEmailDetails() {
//        String email = "test@example.com";
//        String subject = "Test Subject";
//        String message = "Test Message";
//
//        notificationService.sendEmailNotification(email, subject, message);
//    }