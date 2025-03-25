package dev.personal.financial.tracker.controller.user.servlet.handlers;

import dev.personal.financial.tracker.controller.BaseServlet;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import java.util.Map;

@RequiredArgsConstructor
public class UserRequestRouter {

    private final UserRetrievalHandler retrievalHandler;

    public void handleGetUser(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            UserNotFoundException {
        String userIdParam = req.getParameter("id");
        String emailParam = req.getParameter("email");

        if (userIdParam != null) {
            int userId = Integer.parseInt(userIdParam);
            retrievalHandler.handleHetUserById(userId, resp);
        } else if (emailParam != null) {
            retrievalHandler.handleGetUserByEmail(emailParam, resp);
        } else {
            BaseServlet.sendResponse(resp, Map.of("error", "Missing parameters: id or email"), HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
