package dev.personal.financial.tracker.service.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.model.Transaction;

import java.util.List;

public interface TransactionService {
    void addTransaction(TransactionIn transactionIn);
    TransactionOut getTransactionById(String id);
    List<TransactionOut> getTransactionsByUserId(String userId);
    void deleteTransaction(String id);
}