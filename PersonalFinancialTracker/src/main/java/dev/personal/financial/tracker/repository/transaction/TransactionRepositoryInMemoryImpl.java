package dev.personal.financial.tracker.repository.transaction;

import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;
import dev.personal.financial.tracker.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link TransactionRepository}.
 * Хранит транзакции в памяти с использованием HashMap.
 */
public class TransactionRepositoryInMemoryImpl implements TransactionRepository {
    private final Map<Integer, Transaction> transactions = new HashMap<>();

    @Override
    public void save(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    @Override
    public List<Transaction> findByUserId(int userId) {
        return transactions.values().stream()
                .filter(t -> t.getUserId() == (userId))
                .collect(Collectors.toList());
    }

    @Override
    public Transaction findById(int id) {
        Transaction transaction = transactions.get(id);
        if (transaction == null) {
            throw new TransactionNotFoundException(id);
        }
        return transaction;
    }

    @Override
    public void update(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    @Override
    public void delete(int id) {
        transactions.remove(id);
    }

    @Override
    public List<Transaction> findByUserIdAndCategory(int userId, String category) {
        return transactions.values().stream()
                .filter(transaction -> transaction.getUserId() == (userId))
                .filter(transaction -> transaction.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByUserIdAndDate(int userId, LocalDate date) {
        return transactions.values().stream()
                .filter(transaction -> transaction.getUserId() == (userId))
                .filter(transaction -> transaction.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByUserIdAndType(int userId, boolean isIncome) {
        return transactions.values().stream()
                .filter(transaction -> transaction.getUserId() == (userId))
                .filter(transaction -> transaction.isIncome() == isIncome)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalExpensesForCurrentMonth(int userId) {
        LocalDate now = LocalDate.now();
        return transactions.values().stream()
                .filter(transaction -> transaction.getUserId() ==(userId))
                .filter(transaction -> !transaction.isIncome())
                .filter(transaction -> transaction.getDate().getMonth() == now.getMonth())
                .filter(transaction -> transaction.getDate().getYear() == now.getYear())
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<Transaction> findByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate) {
        return transactions.values().stream()
                .filter(t -> t.getUserId() == (userId))
                .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }
}