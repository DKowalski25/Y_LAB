package dev.personal.financial.tracker.exception.goal;

/**
 * Исключение, выбрасываемое при попытке создать цель с уже существующим ID.
 */
public class GoalAlreadyExistsException extends RuntimeException {
    public GoalAlreadyExistsException(int id) {
        super("Цель с id: " + id + " уже существует.");
    }
}