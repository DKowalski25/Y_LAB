package dev.personal.financial.tracker.repository.transaction;

import dev.personal.financial.tracker.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository {
    void save (Transaction transaction);
    List<Transaction> findByUserId(String userId);
    Transaction findById(String id);
    void update(Transaction transaction);
    void delete(String id);
    List<Transaction> findByUserIdAndCategory(String userId, String category);
    List<Transaction> findByUserIdAndDate(String userId, LocalDate date);
    List<Transaction> findByUserIdAndType(String userId, boolean isIncome);
    double getTotalExpensesForCurrentMonth(String userId);
}