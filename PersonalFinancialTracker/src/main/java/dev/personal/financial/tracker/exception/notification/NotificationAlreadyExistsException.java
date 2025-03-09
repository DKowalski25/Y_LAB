package dev.personal.financial.tracker.exception.notification;

/**
 * Исключение, выбрасываемое при попытке создать уведомление с уже существующим ID.
 */
public class NotificationAlreadyExistsException extends RuntimeException {
    public NotificationAlreadyExistsException(String id) {
        super("Уведомление с ID " + id + " уже существует.");
    }
}