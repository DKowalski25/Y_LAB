package dev.personal.financial.tracker.repository.admin;

import dev.personal.financial.tracker.exception.user.UserAlreadyBlockedException;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Реализация интерфейса {@link AdminRepository}.
 * Обрабатывает административные функции, такие как блокировка и удаление пользователей.
 */
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void blockUser(String userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        if (user.getIsBlocked()) {
            throw new UserAlreadyBlockedException(userId);
        }
        user.setIsBlocked(true);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        if (userRepository.findById(userId) == null) {
            throw new UserNotFoundException(userId);
        }
        userRepository.delete(userId);
    }
}