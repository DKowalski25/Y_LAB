package dev.personal.financial.tracker.controller.user.servlet.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.service.user.UserService;

import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class UserRetrievalHandler {

    private final UserService userService;

    public void handleHetUserById(int id, HttpServletResponse resp) throws IOException, UserNotFoundException {
        UserOut user = userService.getUserById(id);
        sendResponse(resp, user, HttpServletResponse.SC_OK);
    }

    public void handleGetUserByEmail(String email, HttpServletResponse resp) throws IOException, UserNotFoundException {
        UserOut user = userService.getUserByEmail(email);
        sendResponse(resp, user, HttpServletResponse.SC_OK);
    }

    private void sendResponse(HttpServletResponse resp, Object data, int statusCode) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(statusCode);
        new ObjectMapper().writeValue(resp.getWriter(), data);
    }

}
