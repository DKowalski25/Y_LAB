package dev.personal.financial.tracker.repository.transaction;

import dev.personal.financial.tracker.exception.transaction.TransactionAlreadyExistsException;
import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;
import dev.personal.financial.tracker.model.Transaction;

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
     * @throws TransactionAlreadyExistsException если транзакция с таким ID уже существует
     */
    void save(Transaction transaction);

    /**
     * Возвращает список всех транзакций пользователя.
     *
     * @param userId ID пользователя
     * @return список транзакций
     */
    List<Transaction> findByUserId(String userId);

    /**
     * Ищет транзакцию по ID.
     *
     * @param id ID транзакции
     * @return найденная транзакция
     * @throws TransactionNotFoundException если транзакция не найдена
     */
    Transaction findById(String id);

    /**
     * Обновляет существующую транзакцию.
     *
     * @param transaction обновленная транзакция
     * @throws TransactionNotFoundException если транзакция не найдена
     */
    void update(Transaction transaction);

    /**
     * Удаляет транзакцию по ID.
     *
     * @param id ID транзакции
     * @throws TransactionNotFoundException если транзакция не найдена
     */
    void delete(String id);

    /**
     * Возвращает список транзакций пользователя по категории.
     *
     * @param userId ID пользователя
     * @param category категория транзакции
     * @return список транзакций
     */
    List<Transaction> findByUserIdAndCategory(String userId, String category);

    /**
     * Возвращает список транзакций пользователя по дате.
     *
     * @param userId ID пользователя
     * @param date дата транзакции
     * @return список транзакций
     */
    List<Transaction> findByUserIdAndDate(String userId, LocalDate date);

    /**
     * Возвращает список транзакций пользователя по типу (доход/расход).
     *
     * @param userId ID пользователя
     * @param isIncome true для доходов, false для расходов
     * @return список транзакций
     */
    List<Transaction> findByUserIdAndType(String userId, boolean isIncome);

    /**
     * Возвращает общую сумму расходов пользователя за текущий месяц.
     *
     * @param userId ID пользователя
     * @return общая сумма расходов
     */
    double getTotalExpensesForCurrentMonth(String userId);

    /**
     * Возвращает список транзакций пользователя за указанный период.
     *
     * @param userId ID пользователя
     * @param startDate начальная дата периода
     * @param endDate конечная дата периода
     * @return список транзакций
     */
    List<Transaction> findByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate);
}