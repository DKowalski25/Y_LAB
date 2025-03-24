package dev.personal.financial.tracker.controller.goal.servlet.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
            sendResponse(resp, Map.of(
                    "error",
                    "Missing required parameters"),
                    HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void sendResponse(HttpServletResponse resp, Object data, int statusCode) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(statusCode);
        new ObjectMapper().writeValue(resp.getWriter(), data);
    }
}
