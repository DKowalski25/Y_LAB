package dev.personal.financial.tracker.controller.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;

import java.util.List;

public interface TransactionController {
    void addTransaction(TransactionIn transactionIn);
    TransactionOut getTransaction(String id);
    List<TransactionOut> getTransactionsByUserId(String userId);
    void updateTransaction(String id, TransactionIn transactionIn);
    void deleteTransaction(String id);
}