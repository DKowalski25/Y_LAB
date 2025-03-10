package dev.personal.financial.tracker.exception.user;

/**
 * Исключение, выбрасываемое при попытке заблокировать уже заблокированного пользователя.
 */
public class UserAlreadyBlockedException extends RuntimeException {
    public UserAlreadyBlockedException(int userId) {
        super("Пользователь с id " + userId + " уже заблокирован.");
    }
}