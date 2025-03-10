package dev.personal.financial.tracker.exception.transaction;

/**
 * Исключение, выбрасываемое при попытке найти несуществующую транзакцию.
 */
public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(int id) {
        super("Транзакция с id: " + id + " не найдена.");
    }
}