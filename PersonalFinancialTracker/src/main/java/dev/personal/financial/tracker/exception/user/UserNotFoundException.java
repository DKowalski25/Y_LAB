package dev.personal.financial.tracker.exception.user;

/**
 * Исключение, выбрасываемое, если пользователь не найден.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("Пользователь с email: " + email + " не найден.");
    }

    public UserNotFoundException(int id) {
        super("Пользователь с id: " + id + " не найден.");
    }

    public UserNotFoundException() {
        super("Пользователь не найден.");
    }
}