package dev.personal.financial.tracker.exception.budget;

/**
 * Исключение, выбрасываемое при попытке создать бюджет с уже существующим ID пользователя.
 */
public class BudgetAlreadyExistsException extends RuntimeException {
    public BudgetAlreadyExistsException(String userId) {
        super("Бюджет для пользователя с ID " + userId + " уже существует.");
    }
}