package dev.personal.financial.tracker.controller.user.servlet;

import dev.personal.financial.tracker.controller.BaseServlet;
import dev.personal.financial.tracker.controller.user.servlet.handlers.UserRequestRouter;
import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.exception.user.UserAlreadyExistsException;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.service.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
//("/api/users")
public class UserServlet extends BaseServlet {

    private final UserService userService;
    private final Validator validator;
    private final UserRequestRouter requestRouter;

    /**
     * Обрабатывает POST-запрос для регистрации пользователя.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            UserIn userIn = parseRequest(req, UserIn.class);

            Set<ConstraintViolation<UserIn>> violations = validator.validate(userIn);
            if (!violations.isEmpty()) {
                sendResponse(resp, violations, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            userService.registerUser(userIn);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (UserAlreadyExistsException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_CONFLICT);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Обрабатывает GET-запрос для получения пользователя по ID или email.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            requestRouter.handleGetUser(req, resp);
        } catch (UserNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_NOT_FOUND);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendResponse(resp, Map.of("error", "Invalid user ID format"), HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Обрабатывает PUT-запрос для обновления пользователя.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String email = req.getParameter("email");
            UserIn userIn = parseRequest(req, UserIn.class);

            Set<ConstraintViolation<UserIn>> violations = validator.validate(userIn);
            if (!violations.isEmpty()) {
                sendResponse(resp, violations, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            userService.updateUser(email, userIn);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (UserNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Обрабатывает DELETE-запрос для удаления пользователя.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String email = req.getParameter("email");
            userService.deleteUserEmail(email);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (UserNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
