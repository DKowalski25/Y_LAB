package dev.personal.financial.tracker.controller.user;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.UserRole;
import dev.personal.financial.tracker.service.user.UserService;
import dev.personal.financial.tracker.util.ConsolePrinter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerImplTest {

    @Mock
    private UserService userService;

    @Mock
    private ConsolePrinter printer;

    @InjectMocks
    private UserControllerImpl userController;

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
    void registerUser_Success() {
        UserIn userIn = new UserIn(
                "1",
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER
        );
        doNothing().when(userService).registerUser(userIn);

        userController.registerUser(userIn);

        verify(userService, times(1)).registerUser(userIn);
    }

    @Test
    void registerUser_ThrowsException() {
        UserIn userIn = new UserIn(
                "1",
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER
        );
        doThrow(new IllegalArgumentException("Invalid user data")).when(userService).registerUser(userIn);

        userController.registerUser(userIn);

        verify(printer, times(1)).printError("Invalid user data");
    }

    @Test
    void getUserById_Success() {
        UserOut userOut = new UserOut(
                "1",
                "John Doe",
                "john@example.com",
                UserRole.USER,
                false
        );
        when(userService.getUserById("1")).thenReturn(userOut);

        UserOut result = userController.getUserById("1");

        assertEquals(userOut, result);
    }

    @Test
    void getUserByEmail_Success() {
        UserOut userOut = new UserOut(
                "1",
                "John Doe",
                "john@example.com",
                UserRole.USER,
                false
        );
        when(userService.getUserByEmail("john@example.com")).thenReturn(userOut);

        UserOut result = userController.getUserByEmail("john@example.com");

        assertEquals(userOut, result);
    }

    @Test
    void getUserByEmail_ThrowsException() {
        when(userService.getUserByEmail("john@example.com")).thenThrow(new IllegalArgumentException("User not found"));

        UserOut result = userController.getUserByEmail("john@example.com");

        assertNull(result);
        verify(printer, times(1)).printError("User not found");
    }

    @Test
    void updateUser_Success() {
        UserIn userIn = new UserIn(
                "1",
                "John Doe Updated",
                "john@example.com",
                "newpassword123",
                UserRole.USER
        );
        doNothing().when(userService).updateUser("john@example.com", userIn);

        userController.updateUser("john@example.com", userIn);

        verify(userService, times(1)).updateUser("john@example.com", userIn);
        verify(printer, times(1)).printSuccess("Профиль успешно обновлен.");
    }

    @Test
    void deleteUserByEmail_Success() {
        doNothing().when(userService).deleteUserEmail("john@example.com");

        userController.deleteUserByEmail("john@example.com");

        verify(userService, times(1)).deleteUserEmail("john@example.com");
        verify(printer, times(1)).printSuccess("Аккаунт успешно удален.");
    }
}