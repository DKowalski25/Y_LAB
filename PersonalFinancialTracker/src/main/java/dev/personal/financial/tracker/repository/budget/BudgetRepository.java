package dev.personal.financial.tracker.repository.budget;


import dev.personal.financial.tracker.exception.budget.BudgetNotFoundException;
import dev.personal.financial.tracker.exception.budget.BudgetAlreadyExistsException;
import dev.personal.financial.tracker.model.Budget;

/**
 * Интерфейс репозитория для работы с бюджетами.
 * Предоставляет методы для сохранения, поиска, обновления и удаления бюджетов.
 */
public interface BudgetRepository {
    /**
     * Сохраняет бюджет.
     *
     * @param budget бюджет для сохранения
     * @throws BudgetAlreadyExistsException если бюджет для пользователя уже существует
     */
    void save(Budget budget);

    /**
     * Ищет бюджет по ID пользователя.
     *
     * @param userId ID пользователя
     * @return найденный бюджет
     * @throws BudgetNotFoundException если бюджет не найден
     */
    Budget findByUserId(String userId);

    /**
     * Обновляет существующий бюджет.
     *
     * @param budget обновленный бюджет
     * @throws BudgetNotFoundException если бюджет не найден
     */
    void update(Budget budget);

    /**
     * Удаляет бюджет по ID пользователя.
     *
     * @param userId ID пользователя
     * @throws BudgetNotFoundException если бюджет не найден
     */
    void delete(String userId);
}