package dev.personal.financial.tracker.integration.repository.notification;

import dev.personal.financial.tracker.data.ModelFactory;
import dev.personal.financial.tracker.model.Notification;
import dev.personal.financial.tracker.repository.notification.NotificationRepository;
import dev.personal.financial.tracker.repository.notification.NotificationRepositoryImpl;
import dev.personal.financial.tracker.util.PostgresTestContainer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class NotificationRepositoryImplIT {

    private static NotificationRepository notificationRepository;

    @BeforeAll
    static void setUp() throws SQLException {
        PostgresTestContainer.start();
        PostgresTestContainer.setUpDatabase();
        PostgresTestContainer.createNotificationTable();

        notificationRepository = new NotificationRepositoryImpl(PostgresTestContainer.getConnection());
    }

    @AfterAll
    static void tearDown() throws SQLException {
        PostgresTestContainer.tearDownDatabase();
        PostgresTestContainer.stop();
    }

    @AfterEach
    void cleanUp() throws SQLException {
        PostgresTestContainer.executeSql("DELETE FROM app.notifications");
    }

    @Test
    void testSaveAndFindByUserId() {
        Notification notification = ModelFactory.createNotification();

        notificationRepository.save(notification);

        List<Notification> notifications = notificationRepository.findByUserId(notification.getUserId());

        assertEquals(1, notifications.size());
        assertEquals(notification.getMessage(), notifications.get(0).getMessage());
    }

    @Test
    void testDelete() {
        Notification notification = ModelFactory.createNotification();

        notificationRepository.save(notification);

        notificationRepository.delete(notification.getId());

        List<Notification> notifications = notificationRepository.findByUserId(notification.getUserId());

        assertTrue(notifications.isEmpty());
    }
}
