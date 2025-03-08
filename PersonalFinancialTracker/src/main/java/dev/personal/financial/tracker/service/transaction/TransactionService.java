package dev.personal.financial.tracker.service.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;

import java.util.List;

public interface TransactionService {
    void addTransaction(TransactionIn transactionIn);
    TransactionOut getTransactionById(String id);
    List<TransactionOut> getTransactionsByUserId(String userId);
    void updateTransaction(String id, TransactionIn transactionIn);
    void deleteTransaction(String id);
}