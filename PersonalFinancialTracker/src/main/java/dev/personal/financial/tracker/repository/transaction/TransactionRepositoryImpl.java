package dev.personal.financial.tracker.repository.transaction;

import dev.personal.financial.tracker.exception.transaction.TransactionAlreadyExistsException;
import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;
import dev.personal.financial.tracker.model.Transaction;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link TransactionRepository}.
 * Хранит транзакции в памяти с использованием HashMap.
 */
public class TransactionRepositoryImpl implements TransactionRepository {
    private final Map<String, Transaction> transactions = new HashMap<>();

    @Override
    public void save(Transaction transaction) {
        if (transactions.containsKey(transaction.getId())) {
            throw new TransactionAlreadyExistsException(transaction.getId());
        }
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
        Transaction transaction = transactions.get(id);
        if (transaction == null) {
            throw new TransactionNotFoundException(id);
        }
        return transaction;
    }

    @Override
    public void update(Transaction transaction) {
        if (!transactions.containsKey(transaction.getId())) {
            throw new TransactionNotFoundException(transaction.getId());
        }
        transactions.put(transaction.getId(), transaction);
    }

    @Override
    public void delete(String id) {
        if (!transactions.containsKey(id)) {
            throw new TransactionNotFoundException(id);
        }
        transactions.remove(id);
    }

    @Override
    public List<Transaction> findByUserIdAndCategory(String userId, String category) {
        return transactions.values().stream()
                .filter(transaction -> transaction.getUserId().equals(userId))
                .filter(transaction -> transaction.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByUserIdAndDate(String userId, LocalDate date) {
        return transactions.values().stream()
                .filter(transaction -> transaction.getUserId().equals(userId))
                .filter(transaction -> transaction.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByUserIdAndType(String userId, boolean isIncome) {
        return transactions.values().stream()
                .filter(transaction -> transaction.getUserId().equals(userId))
                .filter(transaction -> transaction.isIncome() == isIncome)
                .collect(Collectors.toList());
    }

    @Override
    public double getTotalExpensesForCurrentMonth(String userId) {
        LocalDate now = LocalDate.now();
        return transactions.values().stream()
                .filter(transaction -> transaction.getUserId().equals(userId))
                .filter(transaction -> !transaction.isIncome())
                .filter(transaction -> transaction.getDate().getMonth() == now.getMonth())
                .filter(transaction -> transaction.getDate().getYear() == now.getYear())
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    @Override
    public List<Transaction> findByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        return transactions.values().stream()
                .filter(t -> t.getUserId().equals(userId))
                .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }
}