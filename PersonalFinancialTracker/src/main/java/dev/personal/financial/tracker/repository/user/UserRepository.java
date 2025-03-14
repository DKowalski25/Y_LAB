package dev.personal.financial.tracker.repository.user;

import dev.personal.financial.tracker.exception.user.UserAlreadyExistsException;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.model.User;

import java.util.List;

/**
 * Репозиторий для управления пользователями.
 */
public interface UserRepository {
    /**
     * Сохраняет пользователя в хранилище.
     *
     * @param user объект пользователя
     * @throws UserAlreadyExistsException если пользователь с таким email уже существует
     */
    void save(User user);

    /**
     * Ищет пользователя по ID.
     *
     * @param id идентификатор пользователя
     * @return User объект пользователя
     * @throws UserNotFoundException если пользователь не найден
     */
    User findById(int id);

    /**
     * Возвращает список всех пользователей.
     *
     * @return List<User> список пользователей
     */
    List<User> findAll();

    /**
     * Ищет пользователя по email.
     *
     * @param email email пользователя
     * @return User объект пользователя
     * @throws UserNotFoundException если пользователь не найден
     */
    User getByEmail(String email);

    /**
     * Обновляет данные пользователя.
     *
     * @param user объект пользователя с обновленными данными
     */
    void update(User user);

    /**
     * Проверяет, существует ли пользователь с таким email.
     *
     * @param email email пользователя
     * @return true, если пользователь существует, иначе false
     */
    boolean existsByEmail(String email);

    /**
     * Удаляет пользователя по ID.
     *
     * @param id идентификатор пользователя
     */
    void delete(int id);
}