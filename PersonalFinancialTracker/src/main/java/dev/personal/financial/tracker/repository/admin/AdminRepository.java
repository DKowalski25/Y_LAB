package dev.personal.financial.tracker.repository.admin;

import dev.personal.financial.tracker.exception.user.UserAlreadyBlockedException;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.model.User;

import java.util.List;

/**
 * Интерфейс репозитория для работы с административными функциями.
 * Предоставляет методы для управления пользователями.
 */
public interface AdminRepository {
    /**
     * Возвращает список всех пользователей.
     *
     * @return список пользователей
     */
    List<User> getAllUsers();

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