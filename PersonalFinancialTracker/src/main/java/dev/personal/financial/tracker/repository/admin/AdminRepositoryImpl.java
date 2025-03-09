package dev.personal.financial.tracker.repository.admin;

import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

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
        if (user != null) {
            user.setIsBlocked(true);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.delete(userId);
    }
}