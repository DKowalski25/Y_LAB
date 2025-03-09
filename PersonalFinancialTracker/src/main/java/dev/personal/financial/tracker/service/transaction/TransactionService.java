package dev.personal.financial.tracker.service.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    void addTransaction(TransactionIn transactionIn);
    TransactionOut getTransactionById(String id);
    List<TransactionOut> getTransactionsByUserId(String userId);
    void updateTransaction(String id, TransactionIn transactionIn);
    void deleteTransaction(String id);
    List<TransactionOut> getTransactionsByUserIdAndCategory(String userId, String category);
    List<TransactionOut> getTransactionsByUserIdAndDate(String userId, LocalDate date);
    List<TransactionOut> getTransactionsByUserIdAndType(String userId, boolean isIncome);
    double getTotalExpensesForCurrentMonth(String userId);
    List<TransactionOut> getTransactionsByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate);
}