package dev.personal.financial.tracker.controller.admin;

import dev.personal.financial.tracker.dto.user.UserOut;

import java.util.List;

/**
 * Интерфейс контроллера для работы с административными функциями.
 * Предоставляет методы для обработки HTTP-запросов, связанных с управлением пользователями.
 */
public interface AdminController {
    /**
     * Возвращает список всех пользователей.
     *
     * @return список пользователей
     */
    List<UserOut> getAllUsers();

    /**
     * Блокирует пользователя по ID.
     *
     * @param userId ID пользователя
     */
    void blockUser(int userId);

    /**
     * Удаляет пользователя по ID.
     *
     * @param userId ID пользователя
     */
    void deleteUser(int userId);
}