package dev.personal.financial.tracker.controller.goal.servlet.handlers;

import dev.personal.financial.tracker.controller.BaseServlet;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class GoalRequestRouter {
    private final GoalRetrievalHandler retrievalHandler;

    public void handleGetGoal(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, GoalNotFoundException {
        String goalIdParam = req.getParameter("id");
        String userIdParam = req.getParameter("userId");

        if (goalIdParam != null) {
            int goalId = Integer.parseInt(goalIdParam);
            retrievalHandler.handleGetGoalById(goalId, resp);
        } else if (userIdParam != null) {
            int userId = Integer.parseInt(userIdParam);
            retrievalHandler.handleGetGoalByUserId(userId, resp);
        } else {
            BaseServlet.sendResponse(resp, Map.of(
                    "error",
                    "Missing required parameters"),
                    HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
