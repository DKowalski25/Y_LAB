package dev.personal.financial.tracker.controller.transaction.servlet.handlers;

import dev.personal.financial.tracker.controller.BaseServlet;
import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;
import dev.personal.financial.tracker.model.TransactionCategory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RequiredArgsConstructor
public class TransactionRequestRouter {

    private final TransactionRetrievalHandler retrievalHandler;

    public void handleGetTransactions(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            TransactionNotFoundException {
        String transactionIdParam = req.getParameter("id");
        if (transactionIdParam != null) {
            int transactionId = Integer.parseInt(transactionIdParam);
            retrievalHandler.handleGetTransactionById(transactionId, resp);
        } else {
            BaseServlet.sendResponse(resp, Map.of("error", "Missing parameter: id"), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void handleUserTransactions(HttpServletRequest req, HttpServletResponse resp, String pathInfo)
            throws IOException {
        String[] parts = pathInfo.split("/");
        if (parts.length < 3) {
            BaseServlet.sendResponse(resp, Map.of("error", "Invalid URL"), HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int userId = Integer.parseInt(parts[2]);

        if (parts.length == 3) {
            retrievalHandler.handleGetTransactionsByUserId(userId, resp);
        } else if (parts.length == 5 && parts[3].equals("category")) {
            String categoryStr = parts[4];
            try {
                TransactionCategory category = TransactionCategory.valueOf(categoryStr.toUpperCase());
                retrievalHandler.handleGetTransactionsByUserIdAndCategory(userId, category, resp);
            } catch (IllegalArgumentException e) {
                BaseServlet.sendResponse(resp, Map.of("error", "Invalid category: " + categoryStr), HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (parts.length == 5 && parts[3].equals("date")) {
            LocalDate date = LocalDate.parse(parts[4], DateTimeFormatter.ISO_DATE);
            retrievalHandler.handleGetTransactionsByUserIdAndDate(userId, date, resp);
        } else if (parts.length == 5 && parts[3].equals("type")) {
            boolean isIncome = Boolean.parseBoolean(parts[4]);
            retrievalHandler.handleGetTransactionsByUserIdAndType(userId, isIncome, resp);
        } else if (parts.length == 6 && parts[3].equals("period")) {
            LocalDate startDate = LocalDate.parse(parts[4], DateTimeFormatter.ISO_DATE);
            LocalDate endDate = LocalDate.parse(parts[5], DateTimeFormatter.ISO_DATE);
            retrievalHandler.handleGetTransactionsByUserIdAndDateRange(userId, startDate, endDate, resp);
        } else {
            BaseServlet.sendResponse(resp, Map.of("error", "Invalid URL"), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void handleTotalExpenses(HttpServletRequest req, HttpServletResponse resp, String pathInfo)
            throws IOException {
        String[] parts = pathInfo.split("/");
        if (parts.length != 3) {
            BaseServlet.sendResponse(resp, Map.of("error", "Invalid URL"), HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int userId = Integer.parseInt(parts[2]);
        retrievalHandler.handleGetTotalExpensesForCurrentMonth(userId, resp);
    }
}
