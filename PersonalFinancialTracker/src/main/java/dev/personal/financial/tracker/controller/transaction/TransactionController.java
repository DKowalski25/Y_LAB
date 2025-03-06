package dev.personal.financial.tracker.controller.transaction;

import dev.personal.financial.tracker.model.Transaction;

import java.util.List;

public interface TransactionController {
    void addTransaction(Transaction transaction);
    Transaction getTransaction(String id);
    List<Transaction> getTransactionsByUserId(String userId);
    void deleteTransaction(String id);
}