package dev.personal.financial.tracker.exception.transaction;

/**
 * Исключение, выбрасываемое при попытке создать транзакцию с уже существующим ID.
 */
public class TransactionAlreadyExistsException extends RuntimeException {
    public TransactionAlreadyExistsException(int id) {
        super("Транзакция с id: " + id + " уже существует.");
    }
}