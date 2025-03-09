package dev.personal.financial.tracker.dto.user;

import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.model.UserRole;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void toEntity_ShouldMapUserInToUser() {
        UserIn userIn = new UserIn(
                "1",
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER
        );

        User user = UserMapper.toEntity(userIn);

        assertThat(user.getId()).isEqualTo("1");
        assertThat(user.getName()).isEqualTo("John Doe");
        assertThat(user.getEmail()).isEqualTo("john@example.com");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getRole()).isEqualTo(UserRole.USER);
        assertThat(user.getIsBlocked()).isFalse();
    }

    @Test
    void toDto_ShouldMapUserToUserOut() {
        User user = new User(
                "1",
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER,
                false
        );

        UserOut userOut = UserMapper.toDto(user);

        assertThat(userOut.getId()).isEqualTo("1");
        assertThat(userOut.getName()).isEqualTo("John Doe");
        assertThat(userOut.getEmail()).isEqualTo("john@example.com");
        assertThat(userOut.getRole()).isEqualTo(UserRole.USER);
        assertThat(userOut.getIsBlocked()).isFalse();
    }

    @Test
    void updateEntity_ShouldUpdateUserFields() {
        User user = new User(
                "1",
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER,
                false
        );
        UserIn userIn = new UserIn(
                "1",
                "Jane Doe",
                "jane@example.com",
                "newpassword123",
                UserRole.ADMIN
        );

        UserMapper.updateEntity(user, userIn);

        assertThat(user.getName()).isEqualTo("Jane Doe");
        assertThat(user.getEmail()).isEqualTo("jane@example.com");
        assertThat(user.getPassword()).isEqualTo("newpassword123");
        assertThat(user.getRole()).isEqualTo(UserRole.ADMIN);
    }
}