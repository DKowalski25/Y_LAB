package dev.personal.financial.tracker.exception.notification;

/**
 * Исключение, выбрасываемое при попытке создать уведомление с уже существующим ID.
 */
public class NotificationAlreadyExistsException extends RuntimeException {
    public NotificationAlreadyExistsException(int id) {
        super("Уведомление с id: " + id + " уже существует.");
    }
}