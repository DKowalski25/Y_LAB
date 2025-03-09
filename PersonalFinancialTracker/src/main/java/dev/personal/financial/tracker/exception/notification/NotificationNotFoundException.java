package dev.personal.financial.tracker.exception.notification;

/**
 * Исключение, выбрасываемое при попытке найти несуществующее уведомление.
 */
public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(String id) {
        super("Уведомление с ID " + id + " не найдено.");
    }
}