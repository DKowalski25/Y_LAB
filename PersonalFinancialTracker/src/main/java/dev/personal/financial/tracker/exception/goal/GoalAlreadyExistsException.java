package dev.personal.financial.tracker.exception.goal;

/**
 * Исключение, выбрасываемое при попытке создать цель с уже существующим ID.
 */
public class GoalAlreadyExistsException extends RuntimeException {
    public GoalAlreadyExistsException(String id) {
        super("Цель с ID " + id + " уже существует.");
    }
}