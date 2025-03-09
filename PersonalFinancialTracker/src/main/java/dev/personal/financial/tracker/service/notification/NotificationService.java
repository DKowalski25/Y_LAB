package dev.personal.financial.tracker.service.notification;

import dev.personal.financial.tracker.dto.notification.NotificationIn;
import dev.personal.financial.tracker.dto.notification.NotificationOut;

import java.util.List;

public interface NotificationService {
    void sendNotification(NotificationIn notificationIn);
    List<NotificationOut> getNotificationsByUserId(String userId);
    void deleteNotification(String id);
    void sendEmailNotification(String email, String subject, String message);
}