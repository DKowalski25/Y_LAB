package dev.personal.financial.tracker.controller.admin.servlet.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.service.admin.AdminService;

import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class AdminRetrievalHandler {

    private final AdminService adminService;

    public  void handleGetAllUsers(HttpServletResponse resp) throws IOException {
        List<UserOut> users = adminService.getAllUsers();
        sendResponse(resp, users, HttpServletResponse.SC_OK);
    }

    private void sendResponse(HttpServletResponse resp, Object data, int statusCode) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(statusCode);
        new ObjectMapper().writeValue(resp.getWriter(), data);
    }
}
