package dev.personal.financial.tracker.service.user;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserMapper;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.exception.user.UserAlreadyExistsException;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Сервисный слой для работы с пользователями.
 */
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void registerUser(UserIn userIn) {
        String email = userIn.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        User user = UserMapper.toEntity(userIn);
        userRepository.save(user);
    }

    @Override
    public UserOut getUserById(int id) {
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
        UserMapper.updateEntity(existingUser, userIn);
        userRepository.update(existingUser);
    }

    @Override
    public void deleteUserEmail(String email) {
        User user = userRepository.getByEmail(email);
        userRepository.delete(user.getId());
    }
}