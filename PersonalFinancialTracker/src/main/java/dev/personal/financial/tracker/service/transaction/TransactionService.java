package dev.personal.financial.tracker.service.transaction;

import dev.personal.financial.tracker.model.Transaction;

import java.util.List;

public interface TransactionService {
    void addTransaction(Transaction transaction);
    Transaction getTransactionById(String id);
    List<Transaction> getTransactionsByUserId(String userId);
    void deleteTransaction(String id);
}