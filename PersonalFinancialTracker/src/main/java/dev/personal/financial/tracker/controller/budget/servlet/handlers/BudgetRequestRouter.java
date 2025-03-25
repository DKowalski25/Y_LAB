package dev.personal.financial.tracker.controller.budget.servlet.handlers;

import dev.personal.financial.tracker.controller.BaseServlet;
import dev.personal.financial.tracker.exception.budget.BudgetNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class BudgetRequestRouter {

    private final BudgetRetrievalHandler retrievalHandler;

    public void handleGetBudgetByUserId(HttpServletRequest req, HttpServletResponse resp) throws IOException, BudgetNotFoundException {
        String userIdParam = req.getParameter("userId");
        if (userIdParam == null) {
            BaseServlet.sendResponse(resp, Map.of("error", "Missing parameter: userId"), HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int userId = Integer.parseInt(userIdParam);
        retrievalHandler.handleGetBudgetByUserId(userId, resp);
    }
}
