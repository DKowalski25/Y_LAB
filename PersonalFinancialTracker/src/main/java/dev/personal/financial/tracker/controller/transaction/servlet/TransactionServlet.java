package dev.personal.financial.tracker.controller.transaction.servlet;

import dev.personal.financial.tracker.controller.BaseServlet;
import dev.personal.financial.tracker.controller.transaction.servlet.handlers.TransactionRequestRouter;
import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;
import dev.personal.financial.tracker.service.transaction.TransactionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class TransactionServlet extends BaseServlet {

    private final TransactionService transactionService;
    private final Validator validator;
    private final TransactionRequestRouter requestRouter;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            TransactionIn transactionIn = parseRequest(req, TransactionIn.class);

            Set<ConstraintViolation<TransactionIn>> violations = validator.validate(transactionIn);
            if (!violations.isEmpty()) {
                sendResponse(resp, violations, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            transactionService.addTransaction(transactionIn);
            sendResponse(resp, Map.of("message", "Transaction added successfully"), HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                // Обработка запроса на получение транзакции по ID
                requestRouter.handleGetTransactions(req, resp);
            } else if (pathInfo.startsWith("/user/")) {
                // Обработка запросов, связанных с пользователем
                requestRouter.handleUserTransactions(req, resp, pathInfo);
            } else if (pathInfo.startsWith("/total-expenses/")) {
                // Обработка запроса на получение общей суммы расходов
                requestRouter.handleTotalExpenses(req, resp, pathInfo);
            } else {
                sendResponse(resp, Map.of("error", "Invalid URL"), HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (TransactionNotFoundException e) {
            // Обработка ошибки "Транзакция не найдена"
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_NOT_FOUND);
        } catch (NumberFormatException e) {
            // Обработка ошибки неверного формата числа
            sendResponse(resp, Map.of("error", "Invalid ID format"), HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            // Обработка всех остальных ошибок
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String transactionIdParam = req.getParameter("id");
            if (transactionIdParam == null) {
                sendResponse(resp, Map.of("error", "Missing parameter: id"), HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            TransactionIn transactionIn = parseRequest(req, TransactionIn.class);

            Set<ConstraintViolation<TransactionIn>> violations = validator.validate(transactionIn);
            if (!violations.isEmpty()) {
                sendResponse(resp, violations, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            transactionService.updateTransaction(Integer.parseInt(transactionIdParam), transactionIn);

            sendResponse(resp, Map.of("message", "Transaction updated successfully"), HttpServletResponse.SC_OK);
        } catch (TransactionNotFoundException e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String transactionIdParam = req.getParameter("id");
            if (transactionIdParam == null) {
                sendResponse(resp, Map.of("error", "Missing parameter: id"), HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            transactionService.deleteTransaction(Integer.parseInt(transactionIdParam));

            sendResponse(resp, Map.of("message", "Transaction deleted successfully"), HttpServletResponse.SC_NO_CONTENT);
        } catch (TransactionNotFoundException e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
