package dev.personal.financial.tracker.controller.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.model.Transaction;

import dev.personal.financial.tracker.service.transaction.TransactionService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {
    private final TransactionService transactionService;

    @Override
    public void addTransaction(TransactionIn transactionIn) {
        transactionService.addTransaction(transactionIn);
    }

    @Override
    public TransactionOut getTransaction(String id) {
        return transactionService.getTransactionById(id);
    }

    @Override
    public List<TransactionOut> getTransactionsByUserId(String userId) {
        return transactionService.getTransactionsByUserId(userId);
    }

    @Override
    public void deleteTransaction(String id) {
        transactionService.deleteTransaction(id);
    }
}