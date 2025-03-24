package dev.personal.financial.tracker.controller.goal.servlet;

import dev.personal.financial.tracker.controller.BaseServlet;
import dev.personal.financial.tracker.controller.goal.servlet.handlers.GoalRequestRouter;
import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;
import dev.personal.financial.tracker.service.goal.GoalService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class GoalServlet extends BaseServlet {

    private final GoalService goalService;
    private final Validator validator;
    private final GoalRequestRouter requestRouter;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            GoalIn goalIn = parseRequest(req, GoalIn.class);

            Set<ConstraintViolation<GoalIn>> violations = validator.validate(goalIn);
            if (!violations.isEmpty()) {
                sendResponse(resp, violations, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            goalService.addGoal(goalIn);
            sendResponse(resp, Map.of("message", "Goal added successfully"), HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            requestRouter.handleGetGoal(req, resp);
        } catch (GoalNotFoundException e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_NOT_FOUND);
        } catch (NumberFormatException e) {
            sendResponse(resp, Map.of("error", "Invalid goal ID format"), HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String goalIdParam = req.getParameter("id");
            if (goalIdParam == null) {
                sendResponse(resp, Map.of("error", "Missing parameter: id"), HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            GoalIn goal = parseRequest(req, GoalIn.class);

            Set<ConstraintViolation<GoalIn>> violations = validator.validate(goal);
            if (!violations.isEmpty()) {
                sendResponse(resp, violations, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            goalService.updateGoal(goal);
            sendResponse(resp, Map.of("message", "Goal updated successfully"), HttpServletResponse.SC_OK);
        } catch (GoalNotFoundException e) {
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

            goalService.deleteGoalByUserId(Integer.parseInt(userIdParam));
            sendResponse(resp, Map.of("message", "Goal deleted successfully"), HttpServletResponse.SC_NO_CONTENT);
        } catch (GoalNotFoundException e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
