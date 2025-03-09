package dev.personal.financial.tracker.controller.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;

/**
 * Интерфейс контроллера для работы с целями.
 * Предоставляет методы для обработки HTTP-запросов, связанных с целями.
 */
public interface GoalController {
    /**
     * Добавляет новую цель.
     *
     * @param goalIn данные цели
     */
    void addGoal(GoalIn goalIn);

    /**
     * Возвращает цель по ID пользователя.
     *
     * @param userId ID пользователя
     * @return данные цели
     */
    GoalOut getGoalsByUserId(String userId);

    /**
     * Обновляет существующую цель.
     *
     * @param goalIn обновленные данные цели
     */
    void updateGoal(GoalIn goalIn);

    /**
     * Удаляет цель по ID пользователя.
     *
     * @param userId ID пользователя
     */
    void deleteGoalByUserId(String userId);

    /**
     * Обновляет сохраненную сумму для цели.
     *
     * @param goalId ID цели
     * @param amount сумма для добавления
     */
    void updateSavedAmount(String goalId, double amount);

    /**
     * Возвращает прогресс достижения цели.
     *
     * @param goalId ID цели
     * @return прогресс в процентах
     */
    double getProgress(String goalId);
}