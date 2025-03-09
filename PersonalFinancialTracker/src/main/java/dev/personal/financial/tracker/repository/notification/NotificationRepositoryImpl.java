package dev.personal.financial.tracker.repository.notification;

import dev.personal.financial.tracker.model.Notification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NotificationRepositoryImpl implements NotificationRepository {
    private final Map<String, Notification> notifications = new HashMap<>();

    @Override
    public void save(Notification notification) {
        notifications.put(notification.getId(), notification);
    }

    @Override
    public List<Notification> findByUserId(String userId) {
        return notifications.values().stream()
                .filter(notification -> notification.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        notifications.remove(id);
    }
}