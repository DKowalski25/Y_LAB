package dev.personal.financial.tracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Отправляет JSON-ответ клиенту.
     *
     * @param response   объект HttpServletResponse
     * @param data       данные для отправки
     * @param statusCode HTTP-статус код
     * @throws IOException если произошла ошибка при записи в ответ
     */
    public static void sendResponse(HttpServletResponse response, Object data, int statusCode) throws IOException {
        response.setContentType("application/json");
        response.setStatus(statusCode);
        objectMapper.writeValue(response.getWriter(), data);
    }

    /**
     * Парсит JSON из тела запроса в объект указанного класса.
     *
     * @param request объект HttpServletRequest
     * @param clazz   класс, в который нужно преобразовать JSON
     * @param <T>     тип класса
     * @return объект типа T
     * @throws IOException если произошла ошибка при чтении запроса
     */
    protected <T> T parseRequest(HttpServletRequest request, Class<T> clazz) throws IOException {
        return objectMapper.readValue(request.getReader(), clazz);
    }
}
