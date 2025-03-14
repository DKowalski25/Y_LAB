package dev.personal.financial.tracker.service.user;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.exception.user.UserAlreadyExistsException;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;

/**
 * Сервис для работы с пользователями.
 */
public interface UserService {
    /**
     * Регистрирует нового пользователя.
     *
     * @param userIn данные для создания пользователя
     * @throws UserAlreadyExistsException если пользователь с таким email уже существует
     */
    void registerUser(UserIn userIn);

    /**
     * Получает пользователя по ID.
     *
     * @param id идентификатор пользователя
     * @return DTO с данными пользователя
     * @throws UserNotFoundException если пользователь не найден
     */
    UserOut getUserById(int id);

    /**
     * Получает пользователя по email.
     *
     * @param email email пользователя
     * @return DTO с данными пользователя
     * @throws UserNotFoundException если пользователь не найден
     */
    UserOut getUserByEmail(String email);

    /**
     * Обновляет данные пользователя по email.
     *
     * @param email  email пользователя
     * @param userIn новые данные пользователя
     * @throws UserNotFoundException если пользователь не найден
     */
    void updateUser(String email,UserIn userIn);

    /**
     * Удаляет пользователя по email.
     *
     * @param email email пользователя
     * @throws UserNotFoundException если пользователь не найден
     */
    void deleteUserEmail(String email);
}