package dev.personal.financial.tracker.controller.admin.servlet.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class AdminRequestRouter {

    private final AdminRetrievalHandler retrievalHandler;

    public void handleGetAllUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        retrievalHandler.handleGetAllUsers(resp);
    }
}
