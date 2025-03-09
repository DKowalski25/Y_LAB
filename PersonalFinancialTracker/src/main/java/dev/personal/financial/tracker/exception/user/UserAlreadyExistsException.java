package dev.personal.financial.tracker.exception.user;

/**
 * Исключение, выбрасываемое при попытке зарегистрировать пользователя с уже существующим email.
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super("Пользователь с email " + email + " уже существует.");
    }
}