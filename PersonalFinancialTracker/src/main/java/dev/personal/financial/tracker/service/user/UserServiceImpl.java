package dev.personal.financial.tracker.service.user;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserMapper;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void registerUser(UserIn userIn) {
        User user = UserMapper.toEntity(userIn);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Пользователь с email " + user.getEmail() + " уже существует");
        }
        userRepository.save(user);
    }

    @Override
    public UserOut getUserById(String id) {
        User user = userRepository.findById(id);
        return UserMapper.toDto(user);
    }

    @Override
    public UserOut getUserByEmail(String email) {
        User user = userRepository.getByEmail(email);
        return UserMapper.toDto(user);
    }

    @Override
    public void updateUser(String email, UserIn userIn) {
        User existingUser = userRepository.getByEmail(email);
        if (existingUser == null) {
            throw new IllegalArgumentException("Пользователь с email " + email + " не найден");
        }
        UserMapper.updateEntity(existingUser, userIn);
        userRepository.update(existingUser);
    }

    @Override
    public void deleteUserEmail(String email) {
        User user = userRepository.getByEmail(email);
        if (user != null) {
            userRepository.delete(user.getId());
        } else {
            throw new IllegalArgumentException("Пользователь с email " + email + " не найден");
        }

    }
}