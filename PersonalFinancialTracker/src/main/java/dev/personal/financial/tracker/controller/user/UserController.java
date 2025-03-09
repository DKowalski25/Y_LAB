package dev.personal.financial.tracker.controller.user;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserOut;

/**
 * Контроллер для работы с пользователями.
 */
public interface UserController {
    /**
     * Регистрирует пользователя.
     *
     * @param userIn данные нового пользователя
     */
    void registerUser(UserIn userIn);

    /**
     * Получает пользователя по ID.
     *
     * @param id идентификатор пользователя
     * @return DTO с данными пользователя
     */
    UserOut getUserById(String id);

    /**
     * Получает пользователя по email.
     *
     * @param email email пользователя
     * @return DTO с данными пользователя
     */
    UserOut getUserByEmail(String email);

    /**
     * Обновляет данные пользователя по email.
     *
     * @param email  email пользователя
     * @param userIn новые данные пользователя
     */
    void updateUser(String email, UserIn userIn);

    /**
     * Удаляет пользователя по email.
     *
     * @param email email пользователя
     */
    void deleteUserByEmail(String email);
}