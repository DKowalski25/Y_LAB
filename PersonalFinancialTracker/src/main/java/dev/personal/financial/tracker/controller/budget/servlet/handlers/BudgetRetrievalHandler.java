package dev.personal.financial.tracker.controller.budget.servlet.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.personal.financial.tracker.dto.budget.BudgetOut;
import dev.personal.financial.tracker.exception.budget.BudgetNotFoundException;
import dev.personal.financial.tracker.service.budget.BudgetService;

import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class BudgetRetrievalHandler {

    private final BudgetService budgetService;

    public void handleGetBudgetByUserId(int userId, HttpServletResponse resp) throws IOException, BudgetNotFoundException {
        BudgetOut budgetOut = budgetService.getBudgetByUserId(userId);
        sendResponse(resp, budgetOut, HttpServletResponse.SC_OK);
    }

    private void sendResponse(HttpServletResponse resp, Object data, int statusCode) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(statusCode);
        new ObjectMapper().writeValue(resp.getWriter(), data);
    }
}
