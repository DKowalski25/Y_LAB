package dev.personal.financial.tracker.service.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс сервиса для работы с транзакциями.
 * Предоставляет методы для добавления, поиска, обновления и удаления транзакций.
 */
public interface TransactionService {
    /**
     * Добавляет новую транзакцию.
     *
     * @param transactionIn данные транзакции
     */
    void addTransaction(TransactionIn transactionIn);

    /**
     * Возвращает транзакцию по ID.
     *
     * @param id ID транзакции
     * @return данные транзакции
     * @throws TransactionNotFoundException если транзакция не найдена
     */
    TransactionOut getTransactionById(int id);

    /**
     * Возвращает список транзакций пользователя.
     *
     * @param userId ID пользователя
     * @return список транзакций
     */
    List<TransactionOut> getTransactionsByUserId(int userId);

    /**
     * Обновляет существующую транзакцию.
     *
     * @param id ID транзакции
     * @param transactionIn обновленные данные транзакции
     * @throws TransactionNotFoundException если транзакция не найдена
     */
    void updateTransaction(int id, TransactionIn transactionIn);

    /**
     * Удаляет транзакцию по ID.
     *
     * @param id ID транзакции
     * @throws TransactionNotFoundException если транзакция не найдена
     */
    void deleteTransaction(int id);

    /**
     * Возвращает список транзакций пользователя по категории.
     *
     * @param userId ID пользователя
     * @param category категория транзакции
     * @return список транзакций
     */
    List<TransactionOut> getTransactionsByUserIdAndCategory(int userId, String category);

    /**
     * Возвращает список транзакций пользователя по дате.
     *
     * @param userId ID пользователя
     * @param date дата транзакции
     * @return список транзакций
     */
    List<TransactionOut> getTransactionsByUserIdAndDate(int userId, LocalDate date);

    /**
     * Возвращает список транзакций пользователя по типу (доход/расход).
     *
     * @param userId ID пользователя
     * @param isIncome true для доходов, false для расходов
     * @return список транзакций
     */
    List<TransactionOut> getTransactionsByUserIdAndType(int userId, boolean isIncome);

    /**
     * Возвращает общую сумму расходов пользователя за текущий месяц.
     *
     * @param userId ID пользователя
     * @return общая сумма расходов
     */
    BigDecimal getTotalExpensesForCurrentMonth(int userId);

    /**
     * Возвращает список транзакций пользователя за указанный период.
     *
     * @param userId ID пользователя
     * @param startDate начальная дата периода
     * @param endDate конечная дата периода
     * @return список транзакций
     */
    List<TransactionOut> getTransactionsByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate);
}