package dev.personal.financial.tracker.service.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.exception.goal.GoalAlreadyExistsException;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;

/**
 * Интерфейс сервиса для работы с целями.
 * Предоставляет методы для добавления, поиска, обновления и удаления целей.
 */
public interface GoalService {
    /**
     * Добавляет новую цель.
     *
     * @param goalIn данные цели
     * @throws GoalAlreadyExistsException если цель с таким ID уже существует
     */
    void addGoal(GoalIn goalIn);

    /**
     * Возвращает цель по ID.
     *
     * @param id ID цели
     * @return данные цели
     * @throws GoalNotFoundException если цель не найдена
     */
    GoalOut getGoalById(String id);

    /**
     * Возвращает цель по ID пользователя.
     *
     * @param userId ID пользователя
     * @return данные цели
     * @throws GoalNotFoundException если цель не найдена
     */
    GoalOut getGoalsByUserId(String userId);

    /**
     * Обновляет существующую цель.
     *
     * @param goalIn обновленные данные цели
     * @throws GoalNotFoundException если цель не найдена
     */
    void updateGoal(GoalIn goalIn);

    /**
     * Удаляет цель по ID пользователя.
     *
     * @param userId ID пользователя
     * @throws GoalNotFoundException если цель не найдена
     */
    void deleteGoalByUserId(String userId);

    /**
     * Обновляет сохраненную сумму для цели.
     *
     * @param goalId ID цели
     * @param amount сумма для добавления
     * @throws GoalNotFoundException если цель не найдена
     */
    void updateSavedAmount(String goalId, double amount);

    /**
     * Возвращает прогресс достижения цели.
     *
     * @param goalId ID цели
     * @return прогресс в процентах
     * @throws GoalNotFoundException если цель не найдена
     */
    double getProgress(String goalId);
}