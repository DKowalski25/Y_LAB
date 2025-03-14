package dev.personal.financial.tracker.dto.user;

import dev.personal.financial.tracker.model.User;

public class UserMapper {

    public static User toEntity(UserIn userIn) {
        return new User(
                userIn.getId(),
                userIn.getName(),
                userIn.getEmail(),
                userIn.getPassword(),
                userIn.getRole(),
                false
        );
    }

    public static UserOut toDto(User user) {
        return new UserOut(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getIsBlocked()
        );
    }

    public static void updateEntity(User user, UserIn userIn) {
        user.setName(userIn.getName());
        user.setEmail(userIn.getEmail());
        user.setPassword(userIn.getPassword());
        user.setRole(userIn.getRole());
    }
}