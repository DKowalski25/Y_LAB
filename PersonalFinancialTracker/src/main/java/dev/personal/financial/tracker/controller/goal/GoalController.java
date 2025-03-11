package dev.personal.financial.tracker.controller.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;

import java.math.BigDecimal;

/**
 * Интерфейс контроллера для работы с целями.
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
     * @throws GoalNotFoundException если цель не найдена
     */
    GoalOut getGoalsByUserId(int userId);

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
     * @throws GoalNotFoundException если цель не найдена
     */
    void deleteGoalByUserId(int userId);

    /**
     * Обновляет сохраненную сумму для цели.
     *
     * @param goalId ID цели
     * @param amount сумма для добавления
     */
    void updateSavedAmount(int goalId, BigDecimal amount);

    /**
     * Возвращает прогресс достижения цели.
     *
     * @param goalId ID цели
     * @return прогресс в процентах
     */
    double getProgress(int goalId);
}