package dev.personal.financial.tracker.controller.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.model.Transaction;

import java.util.List;

public interface TransactionController {
    void addTransaction(TransactionIn transactionIn);
    TransactionOut getTransaction(String id);
    List<TransactionOut> getTransactionsByUserId(String userId);
    void deleteTransaction(String id);
}