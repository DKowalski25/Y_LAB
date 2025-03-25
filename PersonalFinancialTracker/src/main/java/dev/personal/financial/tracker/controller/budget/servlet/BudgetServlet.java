package dev.personal.financial.tracker.controller.budget.servlet;

import dev.personal.financial.tracker.controller.BaseServlet;
import dev.personal.financial.tracker.controller.budget.servlet.handlers.BudgetRequestRouter;
import dev.personal.financial.tracker.dto.budget.BudgetIn;
import dev.personal.financial.tracker.exception.budget.BudgetNotFoundException;
import dev.personal.financial.tracker.service.budget.BudgetService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class BudgetServlet extends BaseServlet {

    private final BudgetService budgetService;
    private final Validator validator;
    private final BudgetRequestRouter requestRouter;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            BudgetIn budget = parseRequest(req, BudgetIn.class);

            Set<ConstraintViolation<BudgetIn>> violations = validator.validate(budget);
            if (!violations.isEmpty()) {
                sendResponse(resp, violations, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            budgetService.setBudget(budget);
            sendResponse(resp, Map.of("message", "Budget set successfully"), HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String userIdParam = req.getParameter("userId");
            if (userIdParam == null) {
                sendResponse(resp, Map.of("error", "Missing parameter: userId"), HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            requestRouter.handleGetBudgetByUserId(req, resp);
        } catch (BudgetNotFoundException e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_NOT_FOUND);
        } catch (NumberFormatException e) {
            sendResponse(resp, Map.of("error", "Invalid user ID format"), HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String userIdParam = req.getParameter("userId");
            if (userIdParam == null) {
                sendResponse(resp, Map.of("error", "Missing parameter: userId"), HttpServletResponse.SC_BAD_REQUEST);
            }

            BudgetIn budget = parseRequest(req, BudgetIn.class);

            Set<ConstraintViolation<BudgetIn>> violations = validator.validate(budget);
            if (!violations.isEmpty()) {
                sendResponse(resp, violations, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            budgetService.updateBudget(budget);
            sendResponse(resp, Map.of("message", "Budget updated successfully"), HttpServletResponse.SC_OK);
        } catch (BudgetNotFoundException e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String userIdParam = req.getParameter("userId");
            if (userIdParam == null) {
                sendResponse(resp, Map.of("error", "Missing parameter: userId"), HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            budgetService.deleteBudget(Integer.parseInt(userIdParam));
            sendResponse(resp, Map.of("message", "Budget deleted successfully"), HttpServletResponse.SC_NO_CONTENT);
        } catch (BudgetNotFoundException e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
