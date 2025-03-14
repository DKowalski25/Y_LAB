package dev.personal.financial.tracker.exception.goal;

/**
 * Исключение, выбрасываемое при попытке найти несуществующую цель.
 */
public class GoalNotFoundException extends RuntimeException {
    public GoalNotFoundException() {
        super("Цель не найдена.");
    }
}