package dev.personal.financial.tracker.repository.user;

import dev.personal.financial.tracker.model.User;

import java.util.List;

public interface UserRepository {
    void save(User user);
    User findById(String id);
    List<User> findAll();
    User getByEmail(String email);
    void delete(String id);
}