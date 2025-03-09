package dev.personal.financial.tracker.repository.notification;

import dev.personal.financial.tracker.model.Notification;

import java.util.List;

public interface NotificationRepository {
    void save(Notification notification);
    List<Notification> findByUserId(String userId);
    void delete(String id);
}