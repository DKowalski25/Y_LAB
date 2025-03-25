package dev.personal.financial.tracker.controller.transaction.servlet.handlers;

import dev.personal.financial.tracker.controller.BaseServlet;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;
import dev.personal.financial.tracker.model.TransactionCategory;
import dev.personal.financial.tracker.service.transaction.TransactionService;

import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class TransactionRetrievalHandler {

    private final TransactionService transactionService;

    public void handleGetTransactionById(int transactionId, HttpServletResponse resp)
            throws IOException, TransactionNotFoundException {
        TransactionOut transaction = transactionService.getTransactionById(transactionId);
        BaseServlet.sendResponse(resp, transaction, HttpServletResponse.SC_OK);
    }

    public void handleGetTransactionsByUserId(int userId, HttpServletResponse resp) throws IOException {
        List<TransactionOut> transactions = transactionService.getTransactionsByUserId(userId);
        BaseServlet.sendResponse(resp, transactions, HttpServletResponse.SC_OK);
    }

    public void handleGetTransactionsByUserIdAndCategory(int userId, TransactionCategory category,
                                                         HttpServletResponse resp) throws IOException {
        List<TransactionOut> transactions = transactionService.getTransactionsByUserIdAndCategory(userId, category);
        BaseServlet.sendResponse(resp, transactions, HttpServletResponse.SC_OK);
    }

    public void handleGetTransactionsByUserIdAndDate(int userId, LocalDate date, HttpServletResponse resp)
            throws IOException {
        List<TransactionOut> transactions = transactionService.getTransactionsByUserIdAndDate(userId, date);
        BaseServlet.sendResponse(resp, transactions, HttpServletResponse.SC_OK);
    }

    public void handleGetTransactionsByUserIdAndType(int userId, boolean isIncome, HttpServletResponse resp)
            throws IOException {
        List<TransactionOut> transactions = transactionService.getTransactionsByUserIdAndType(userId, isIncome);
        BaseServlet.sendResponse(resp, transactions, HttpServletResponse.SC_OK);
    }

    public void handleGetTransactionsByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate,
                                                          HttpServletResponse resp) throws IOException {
        List<TransactionOut> transactions = transactionService.getTransactionsByUserIdAndDateRange(
                userId,
                startDate,
                endDate);
        BaseServlet.sendResponse(resp, transactions, HttpServletResponse.SC_OK);
    }

    public void handleGetTotalExpensesForCurrentMonth(int userId, HttpServletResponse resp) throws IOException {
        BigDecimal totalExpenses = transactionService.getTotalExpensesForCurrentMonth(userId);
        sendResponse(resp, Map.of("totalExpenses", totalExpenses), HttpServletResponse.SC_OK);
    }
}
