package dev.personal.financial.tracker.service.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionMapper;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;
import dev.personal.financial.tracker.model.Transaction;
import dev.personal.financial.tracker.repository.transaction.TransactionRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link TransactionService}.
 * Обрабатывает бизнес-логику, связанную с транзакциями.
 */
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public void addTransaction(TransactionIn transactionIn) {
        Transaction transaction = TransactionMapper.toEntity(transactionIn);
        transactionRepository.save(transaction);
    }

    @Override
    public TransactionOut getTransactionById(String id) {
        return TransactionMapper.toDto(transactionRepository.findById(id));
    }

    @Override
    public List<TransactionOut> getTransactionsByUserId(String userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        return transactions.stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateTransaction(String id, TransactionIn transactionIn) {
        Transaction existingTransaction = transactionRepository.findById(id);
        if (existingTransaction == null) {
            throw new TransactionNotFoundException("Транзакция с id " + id + " не найдена");
        }
        TransactionMapper.updateTransaction(existingTransaction, transactionIn);
        transactionRepository.save(existingTransaction);
    }

    @Override
    public void deleteTransaction(String id) {
        transactionRepository.findById(id);
        transactionRepository.delete(id);
    }

    @Override
    public List<TransactionOut> getTransactionsByUserIdAndCategory(String userId, String category) {
        List<Transaction> transactions = transactionRepository.findByUserIdAndCategory(userId, category);
        return transactions.stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionOut> getTransactionsByUserIdAndDate(String userId, LocalDate date) {
        List<Transaction> transactions = transactionRepository.findByUserIdAndDate(userId, date);
        return transactions.stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionOut> getTransactionsByUserIdAndType(String userId, boolean isIncome) {
        List<Transaction> transactions = transactionRepository.findByUserIdAndType(userId, isIncome);
        return transactions.stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public double getTotalExpensesForCurrentMonth(String userId) {
        return transactionRepository.getTotalExpensesForCurrentMonth(userId);
    }

    @Override
    public List<TransactionOut> getTransactionsByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionRepository.findByUserIdAndDateRange(userId, startDate, endDate);
        return transactions.stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }
}