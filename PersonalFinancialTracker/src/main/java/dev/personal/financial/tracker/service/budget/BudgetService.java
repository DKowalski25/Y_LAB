package dev.personal.financial.tracker.service.budget;

import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.exception.budget.BudgetAlreadyExistsException;
import dev.personal.financial.tracker.exception.budget.BudgetNotFoundException;

/**
 * Интерфейс сервиса для работы с бюджетами.
 * Предоставляет методы для добавления, поиска, обновления и удаления бюджетов.
 */
public interface BudgetService {
    /**
     * Устанавливает бюджет для пользователя.
     *
     * @param budgetIn данные бюджета
     * @throws BudgetAlreadyExistsException если бюджет для пользователя уже существует
     */
    void setBudget(BudgetIn budgetIn);

    /**
     * Возвращает бюджет по ID пользователя.
     *
     * @param userId ID пользователя
     * @return данные бюджета
     * @throws BudgetNotFoundException если бюджет не найден
     */
    BudgetOut getBudgetByUserId(String userId);

    /**
     * Обновляет существующий бюджет.
     *
     * @param budgetIn обновленные данные бюджета
     * @throws BudgetNotFoundException если бюджет не найден
     */
    void updateBudget(BudgetIn budgetIn);

    /**
     * Удаляет бюджет по ID пользователя.
     *
     * @param userId ID пользователя
     * @throws BudgetNotFoundException если бюджет не найден
     */
    void deleteBudget(String userId);
}