package dev.personal.financial.tracker.controller.goal.servlet.handlers;

import dev.personal.financial.tracker.controller.BaseServlet;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;
import dev.personal.financial.tracker.service.goal.GoalService;

import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class GoalRetrievalHandler {

    private final GoalService goalService;

    public void handleGetGoalById(int id, HttpServletResponse resp) throws IOException, GoalNotFoundException {
        GoalOut goal = goalService.getGoalById(id);
        BaseServlet.sendResponse(resp, goal, HttpServletResponse.SC_OK);
    }

    public void handleGetGoalByUserId(int id, HttpServletResponse resp) throws IOException, GoalNotFoundException {
        GoalOut goal = goalService.getGoalsByUserId(id);
        BaseServlet.sendResponse(resp, goal, HttpServletResponse.SC_OK);
    }
}
