package dev.personal.financial.tracker.repository.transaction;

import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;
import dev.personal.financial.tracker.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс репозитория для работы с транзакциями.
 * Предоставляет методы для сохранения, поиска, обновления и удаления транзакций.
 */
public interface TransactionRepository {
    /**
     * Сохраняет транзакцию.
     *
     * @param transaction транзакция для сохранения
     */
    void save(Transaction transaction);

    /**
     * Возвращает список всех транзакций пользователя.
     *
     * @param userId ID пользователя
     * @return список транзакций
     */
    List<Transaction> findByUserId(int userId);

    /**
     * Ищет транзакцию по ID.
     *
     * @param id ID транзакции
     * @return найденная транзакция
     * @throws TransactionNotFoundException если транзакция не найдена
     */
    Transaction findById(int id);

    /**
     * Обновляет существующую транзакцию.
     *
     * @param transaction обновленная транзакция
     */
    void update(Transaction transaction);

    /**
     * Удаляет транзакцию по ID.
     *
     * @param id ID транзакции
     */
    void delete(int id);

    /**
     * Возвращает список транзакций пользователя по категории.
     *
     * @param userId ID пользователя
     * @param category категория транзакции
     * @return список транзакций
     */
    List<Transaction> findByUserIdAndCategory(int userId, String category);

    /**
     * Возвращает список транзакций пользователя по дате.
     *
     * @param userId ID пользователя
     * @param date дата транзакции
     * @return список транзакций
     */
    List<Transaction> findByUserIdAndDate(int userId, LocalDate date);

    /**
     * Возвращает список транзакций пользователя по типу (доход/расход).
     *
     * @param userId ID пользователя
     * @param isIncome true для доходов, false для расходов
     * @return список транзакций
     */
    List<Transaction> findByUserIdAndType(int userId, boolean isIncome);

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
    List<Transaction> findByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate);
}