package dev.personal.financial.tracker.service.admin;

import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.exception.user.UserAlreadyBlockedException;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;

import java.util.List;

/**
 * Интерфейс сервиса для работы с административными функциями.
 * Предоставляет методы для управления пользователями.
 */
public interface AdminService {
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
     * @throws UserNotFoundException если пользователь не найден
     * @throws UserAlreadyBlockedException если пользователь уже заблокирован
     */
    void blockUser(String userId);

    /**
     * Удаляет пользователя по ID.
     *
     * @param userId ID пользователя
     * @throws UserNotFoundException если пользователь не найден
     */
    void deleteUser(String userId);
}