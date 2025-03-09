package dev.personal.financial.tracker.controller.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.service.transaction.TransactionService;
import dev.personal.financial.tracker.util.ConsolePrinter;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {
    private final TransactionService transactionService;
    private final ConsolePrinter printer;

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
    public void updateTransaction(String id, TransactionIn transactionIn) {
        try {
            transactionService.updateTransaction(id, transactionIn);
            printer.printSuccess("Транзакция успешно обновлена.");
        } catch (IllegalArgumentException e) {
            printer.printError(e.getMessage());
        }

    }

    @Override
    public void deleteTransaction(String id) {
        transactionService.deleteTransaction(id);
    }

    @Override
    public List<TransactionOut> getTransactionsByUserIdAndCategory(String userId, String category) {
        return transactionService.getTransactionsByUserIdAndCategory(userId, category);
    }

    @Override
    public List<TransactionOut> getTransactionsByUserIdAndDate(String userId, LocalDate date) {
        return transactionService.getTransactionsByUserIdAndDate(userId, date);
    }

    @Override
    public List<TransactionOut> getTransactionsByUserIdAndType(String userId, boolean type) {
        return transactionService.getTransactionsByUserIdAndType(userId, type);
    }

    @Override
    public double getTotalExpensesForCurrentMonth(String userId) {
        return transactionService.getTotalExpensesForCurrentMonth(userId);
    }

    @Override
    public List<TransactionOut> getTransactionsByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        return transactionService.getTransactionsByUserIdAndDateRange(userId, startDate, endDate);
    }
}