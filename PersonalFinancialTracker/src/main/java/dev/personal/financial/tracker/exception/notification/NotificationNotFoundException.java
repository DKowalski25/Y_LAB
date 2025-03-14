package dev.personal.financial.tracker.exception.notification;

/**
 * Исключение, выбрасываемое при попытке найти несуществующее уведомление.
 */
public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(int id) {
        super("Уведомление с id: " + id + " не найдено.");
    }
}