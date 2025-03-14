package dev.personal.financial.tracker.exception.budget;

/**
 * Исключение, выбрасываемое при попытке создать бюджет с уже существующим ID пользователя.
 */
public class BudgetAlreadyExistsException extends RuntimeException {
    public BudgetAlreadyExistsException(int userId) {
        super("Бюджет для пользователя с id: " + userId + " уже существует.");
    }
}