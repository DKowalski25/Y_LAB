package dev.personal.financial.tracker.repository.notification;

import dev.personal.financial.tracker.exception.notification.NotificationAlreadyExistsException;
import dev.personal.financial.tracker.exception.notification.NotificationNotFoundException;
import dev.personal.financial.tracker.model.Notification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link NotificationRepository}.
 * Хранит уведомления в памяти с использованием HashMap.
 */
public class NotificationRepositoryImpl implements NotificationRepository {
    private final Map<String, Notification> notifications = new HashMap<>();

    @Override
    public void save(Notification notification) {
        if (notifications.containsKey(notification.getId())) {
            throw new NotificationAlreadyExistsException(notification.getId());
        }
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
        if (!notifications.containsKey(id)) {
            throw new NotificationNotFoundException(id);
        }
        notifications.remove(id);
    }
}