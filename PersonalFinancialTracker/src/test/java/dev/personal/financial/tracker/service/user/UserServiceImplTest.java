package dev.personal.financial.tracker.service.user;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.model.UserRole;
import dev.personal.financial.tracker.repository.user.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void registerUser_ShouldSaveUser_WhenEmailIsUnique() {
        UserIn userIn = new UserIn(
                "1",
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER
        );
        User user = new User(
                "1",
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER,
                false
        );

        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        doNothing().when(userRepository).save(any(User.class));

        userService.registerUser(userIn);

        verify(userRepository, times(1)).existsByEmail("john@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenEmailAlreadyExists() {
        UserIn userIn = new UserIn(
                "1",
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER
        );

        when(userRepository.existsByEmail("john@example.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.registerUser(userIn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Пользователь с email john@example.com уже существует.");

        verify(userRepository, times(1)).existsByEmail("john@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserById_ShouldReturnUserOut_WhenUserExists() {
        User user = new User(
                "1",
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER,
                false
        );
        when(userRepository.findById("1")).thenReturn(user);

        UserOut result = userService.getUserById("1");

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("john@example.com");
        assertThat(result.getName()).isEqualTo("John Doe");
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findById("1")).thenThrow(new UserNotFoundException("1"));

        assertThatThrownBy(() -> userService.getUserById("1"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Пользователь с email 1 не найден.");

        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void getUserByEmail_ShouldReturnUserOut_WhenUserExists() {
        User user = new User(
                "1",
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER,
                false
        );
        when(userRepository.getByEmail("john@example.com")).thenReturn(user);

        UserOut result = userService.getUserByEmail("john@example.com");

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("john@example.com");
        assertThat(result.getName()).isEqualTo("John Doe");
        verify(userRepository, times(1)).getByEmail("john@example.com");
    }

    @Test
    void getUserByEmail_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.getByEmail("john@example.com")).thenThrow(new UserNotFoundException("john@example.com"));

        assertThatThrownBy(() -> userService.getUserByEmail("john@example.com"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Пользователь с email john@example.com не найден.");

        verify(userRepository, times(1)).getByEmail("john@example.com");
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        UserIn userIn = new UserIn(
                "1",
                "Jane Doe",
                "jane@example.com",
                "newpassword123",
                UserRole.ADMIN
        );
        User existingUser = new User(
                "1",
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER,
                false
        );

        when(userRepository.getByEmail("john@example.com")).thenReturn(existingUser);

        userService.updateUser("john@example.com", userIn);

        verify(userRepository, times(1)).getByEmail("john@example.com");
        verify(userRepository, times(1)).update(existingUser);
        assertThat(existingUser.getEmail()).isEqualTo("jane@example.com");
        assertThat(existingUser.getName()).isEqualTo("Jane Doe");
        assertThat(existingUser.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
        UserIn userIn = new UserIn(
                "1",
                "Jane Doe",
                "jane@example.com",
                "newpassword123",
                UserRole.ADMIN
        );

        when(userRepository.getByEmail("john@example.com")).thenThrow(new UserNotFoundException("john@example.com"));

        assertThatThrownBy(() -> userService.updateUser("john@example.com", userIn))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Пользователь с email john@example.com не найден.");

        verify(userRepository, times(1)).getByEmail("john@example.com");
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    void deleteUserEmail_ShouldDeleteUser_WhenUserExists() {
        User user = new User(
                "1",
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER,
                false
        );
        when(userRepository.getByEmail("john@example.com")).thenReturn(user);

        userService.deleteUserEmail("john@example.com");

        verify(userRepository, times(1)).getByEmail("john@example.com");
        verify(userRepository, times(1)).delete("1");
    }

    @Test
    void deleteUserEmail_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.getByEmail("john@example.com")).thenThrow(new UserNotFoundException("john@example.com"));

        assertThatThrownBy(() -> userService.deleteUserEmail("john@example.com"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Пользователь с email john@example.com не найден.");

        verify(userRepository, times(1)).getByEmail("john@example.com");
        verify(userRepository, never()).delete(any(String.class));
    }
}