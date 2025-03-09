package dev.personal.financial.tracker.exception.user;

/**
 * Исключение, выбрасываемое при попытке заблокировать уже заблокированного пользователя.
 */
public class UserAlreadyBlockedException extends RuntimeException {
    public UserAlreadyBlockedException(String userId) {
        super("Пользователь с ID " + userId + " уже заблокирован.");
    }
}