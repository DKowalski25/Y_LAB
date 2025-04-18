package dev.personal.financial.tracker.exception.budget;

/**
 * Исключение, выбрасываемое при попытке найти несуществующий бюджет.
 */
public class BudgetNotFoundException extends RuntimeException {
    public BudgetNotFoundException(int userId) {
        super("Бюджет для пользователя с id: " + userId + " не найден.");
    }
}