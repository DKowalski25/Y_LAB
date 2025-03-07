package dev.personal.financial.tracker.service.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionMapper;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.model.Transaction;
import dev.personal.financial.tracker.repository.transaction.TransactionRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
    public void deleteTransaction(String id) {
        transactionRepository.delete(id);
    }
}