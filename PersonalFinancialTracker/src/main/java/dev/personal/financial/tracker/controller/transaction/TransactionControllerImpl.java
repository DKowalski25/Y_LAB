package dev.personal.financial.tracker.controller.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;
import dev.personal.financial.tracker.service.transaction.TransactionService;
import dev.personal.financial.tracker.util.ConsolePrinter;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Реализация интерфейса {@link TransactionController}.
 */
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {
    private final TransactionService transactionService;
    private final ConsolePrinter printer;

    @Override
    public void addTransaction(TransactionIn transactionIn) {
        try {
            transactionService.addTransaction(transactionIn);
            printer.printSuccess("Транзакция успешно добавлена.");
        } catch (Exception e) {
            printer.printError("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public TransactionOut getTransaction(int id) {
        try {
            return transactionService.getTransactionById(id);
        } catch (TransactionNotFoundException e) {
            printer.printError("Ошибка: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<TransactionOut> getTransactionsByUserId(int userId) {
        return transactionService.getTransactionsByUserId(userId);
    }

    @Override
    public void updateTransaction(int id, TransactionIn transactionIn) {
        try {
            transactionService.updateTransaction(id, transactionIn);
            printer.printSuccess("Транзакция успешно обновлена.");
        } catch (TransactionNotFoundException e) {
            printer.printError("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public void deleteTransaction(int id) {
            transactionService.deleteTransaction(id);
            printer.printSuccess("Транзакция успешно удалена.");
    }

    @Override
    public List<TransactionOut> getTransactionsByUserIdAndCategory(int userId, String category) {
        return transactionService.getTransactionsByUserIdAndCategory(userId, category);
    }

    @Override
    public List<TransactionOut> getTransactionsByUserIdAndDate(int userId, LocalDate date) {
        return transactionService.getTransactionsByUserIdAndDate(userId, date);
    }

    @Override
    public List<TransactionOut> getTransactionsByUserIdAndType(int userId, boolean type) {
        return transactionService.getTransactionsByUserIdAndType(userId, type);
    }

    @Override
    public BigDecimal getTotalExpensesForCurrentMonth(int userId) {
        return transactionService.getTotalExpensesForCurrentMonth(userId);
    }

    @Override
    public List<TransactionOut> getTransactionsByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate) {
        return transactionService.getTransactionsByUserIdAndDateRange(userId, startDate, endDate);
    }
}