package dev.personal.financial.tracker.repository.user;

import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.model.User;

import java.util.*;

/**
 * Реализация репозитория пользователей с хранением данных в памяти.
 */
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        if (existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Пользователь с email " + user.getEmail() + " уже существует.");
        }
        users.put(user.getId(), user);
    }

    @Override
    public User findById(String id) {
        return Optional.ofNullable(users.get(id))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public void update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException(user.getId());
        }
        users.put(user.getId(), user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.values().stream().anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public void delete(String id) {
        if (users.remove(id) == null) {
            throw new UserNotFoundException(id);
        }
    }
}