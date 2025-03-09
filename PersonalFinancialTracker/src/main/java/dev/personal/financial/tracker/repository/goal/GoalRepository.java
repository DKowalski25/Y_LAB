package dev.personal.financial.tracker.repository.goal;

import dev.personal.financial.tracker.exception.goal.GoalAlreadyExistsException;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;
import dev.personal.financial.tracker.model.Goal;

/**
 * Интерфейс репозитория для работы с целями.
 * Предоставляет методы для сохранения, поиска, обновления и удаления целей.
 */
public interface GoalRepository {
    /**
     * Сохраняет цель.
     *
     * @param goal цель для сохранения
     * @throws GoalAlreadyExistsException если цель с таким ID уже существует
     */
    void save(Goal goal);

    /**
     * Ищет цель по ID.
     *
     * @param id ID цели
     * @return найденная цель
     * @throws GoalNotFoundException если цель не найдена
     */
    Goal findById(String id);

    /**
     * Ищет цель по ID пользователя.
     *
     * @param userId ID пользователя
     * @return найденная цель
     * @throws GoalNotFoundException если цель не найдена
     */
    Goal findByUserId(String userId);

    /**
     * Обновляет существующую цель.
     *
     * @param goal обновленная цель
     * @throws GoalNotFoundException если цель не найдена
     */
    void update(Goal goal);

    /**
     * Удаляет цель по ID пользователя.
     *
     * @param userId ID пользователя
     * @throws GoalNotFoundException если цель не найдена
     */
    void deleteByUserId(String userId);

    /**
     * Обновляет сохраненную сумму для цели.
     *
     * @param goalId ID цели
     * @param amount сумма для добавления
     * @throws GoalNotFoundException если цель не найдена
     */
    void updateSavedAmount(String goalId, double amount);

    /**
     * Возвращает сохраненную сумму для цели.
     *
     * @param goalId ID цели
     * @return сохраненная сумма
     * @throws GoalNotFoundException если цель не найдена
     */
    double getSavedAmount(String goalId);
}