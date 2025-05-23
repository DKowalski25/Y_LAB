package dev.personal.financial.tracker.controller.budget;

import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.exception.budget.BudgetNotFoundException;

/**
 * Интерфейс контроллера для работы с бюджетами.
 */
public interface BudgetController {
    /**
     * Устанавливает бюджет для пользователя.
     *
     * @param budgetIn данные бюджета
     */
    void setBudget(BudgetIn budgetIn);

    /**
     * Возвращает бюджет по ID пользователя.
     *
     * @param userId ID пользователя
     * @return данные бюджета
     * @throws BudgetNotFoundException если бюджет не найден
     */
    BudgetOut getBudgetByUserId(int userId);

    /**
     * Обновляет существующий бюджет.
     *
     * @param budgetIn обновленные данные бюджета
     */
    void updateBudget(BudgetIn budgetIn);

    /**
     * Удаляет бюджет по ID пользователя.
     *
     * @param userId ID пользователя
     */
    void deleteBudget(int userId);
}