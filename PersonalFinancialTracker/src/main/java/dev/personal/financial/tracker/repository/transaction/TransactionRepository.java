package dev.personal.financial.tracker.repository.transaction;

import dev.personal.financial.tracker.model.Transaction;

import java.util.List;

public interface TransactionRepository {
    void save (Transaction transaction);
    List<Transaction> findByUserId(String userId);
    Transaction findById(String id);
    void delete(String id);
}