package dev.personal.financial.tracker.data;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.model.UserRole;

import java.util.UUID;

public class ModelFactory {
    // TODO: доделать класс реализовать тесты через него

    private final static int ID = UUID.randomUUID().hashCode();
    private final static int USER_ID = UUID.randomUUID().hashCode();

    public static User createUser(UserRole role) {
        int id = UUID.randomUUID().hashCode();
        return new User(
                id,
                "John Doe",
                "john@example.com",
                "password123",
                role,
                false
        );
    }

    public static UserIn createUserIn(UserRole role) {
        return new UserIn(
                ID,
                "John Doe",
                "john@example.com",
                "newpassword123",
                role
        );
    }

    public static UserOut createUserOut(UserRole role,Boolean isBlocked) {
        return new UserOut(
                ID,
                "John Doe",
                "john@example.com",
                role,
                isBlocked
        );
    }
}
