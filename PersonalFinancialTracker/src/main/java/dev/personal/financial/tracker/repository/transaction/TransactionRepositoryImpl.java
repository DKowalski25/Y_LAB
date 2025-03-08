package dev.personal.financial.tracker.repository.transaction;

import dev.personal.financial.tracker.model.Transaction;

import java.time.LocalDate;
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

    @Override
    public List<Transaction> findByUserIdAndCategory(String userId, String category) {
        return transactions.values().stream()
                .filter(transaction -> transaction.getUserId().equals(userId)
                        && transaction.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByUserIdAndDate(String userId, LocalDate date) {
        return transactions.values().stream()
                .filter(transaction -> transaction.getUserId().equals(userId)
                        && transaction.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByUserIdAndType(String userId, boolean isIncome) {
        return transactions.values().stream()
                .filter(transaction -> transaction.getUserId().equals(userId)
                        && transaction.isIncome() == isIncome)
                .collect(Collectors.toList()
        );
    }

    @Override
    public double getTotalExpensesForCurrentMonth(String userId) {
        LocalDate now = LocalDate.now();
        return transactions.values().stream()
                .filter(transaction -> transaction.getUserId().equals(userId))
                .filter(transaction -> !transaction.isIncome())
                .filter(transaction -> transaction.getDate().getMonth() == now.getMonth()
                        && transaction.getDate().getYear() == now.getYear())
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}