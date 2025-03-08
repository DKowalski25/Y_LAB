package dev.personal.financial.tracker.repository.transaction;

import dev.personal.financial.tracker.model.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionRepositoryImpl implements TransactionRepository {
    private Map<String, Transaction> transactions = new HashMap<>();

    @Override
    public void save(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    @Override
    public List<Transaction> findByUserId(String userId) {
        return transactions.values().stream()
                .filter(t -> t.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Transaction findById(String id) {
        return transactions.get(id);
    }

    @Override
    public void update(Transaction transaction) {
        if (transactions.containsKey(transaction.getId())) {
            transactions.put(transaction.getId(), transaction);
        }
    }

    @Override
    public void delete(String id) {
        transactions.remove(id);
    }
}