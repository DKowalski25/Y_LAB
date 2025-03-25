package dev.personal.financial.tracker.dto.user;

import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.model.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public static User toEntity(UserIn userIn) {
        return new User(
                userIn.getId(),
                userIn.getName(),
                userIn.getEmail(),
                userIn.getPassword(),
                UserRole.USER,
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
//        user.setRole(userIn.getRole());
    }

    public static User mapRowToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(UserRole.valueOf(resultSet.getString("role")));
        user.setIsBlocked(resultSet.getBoolean("is_blocked"));
        return user;
    }
}