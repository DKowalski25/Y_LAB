package dev.personal.financial.tracker.controller.admin.servlet;

import dev.personal.financial.tracker.controller.BaseServlet;
import dev.personal.financial.tracker.controller.admin.servlet.handlers.AdminRequestRouter;
import dev.personal.financial.tracker.exception.user.UserAlreadyBlockedException;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.service.admin.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validator;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class AdminServlet extends BaseServlet {

    private final AdminService adminService;
    private final Validator validator;
    private final AdminRequestRouter requestRouter;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String userIdParam = req.getParameter("userId");
            if (userIdParam == null) {
                sendResponse(resp, Map.of("error", "Missing parameter: userId"), HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            adminService.blockUser(Integer.parseInt(userIdParam));
            sendResponse(resp, Map.of("message", "User blocked successfully"), HttpServletResponse.SC_OK);
        } catch (UserAlreadyBlockedException e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            requestRouter.handleGetAllUsers(req, resp);
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

            adminService.deleteUser(Integer.parseInt(userIdParam));
            sendResponse(resp, Map.of("message", "User deleted successfully"), HttpServletResponse.SC_OK);
        } catch (UserNotFoundException e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, Map.of("error", e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
