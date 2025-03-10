package dev.personal.financial.tracker.controller.user;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.exception.user.UserAlreadyExistsException;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.model.UserRole;
import dev.personal.financial.tracker.service.user.UserService;
import dev.personal.financial.tracker.util.ConsolePrinter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerImplTest {

    private final static int ID = UUID.randomUUID().hashCode();

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
                ID,
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER
        );
        doNothing().when(userService).registerUser(userIn);

        userController.registerUser(userIn);

        verify(userService, times(1)).registerUser(userIn);
        verify(printer, times(1)).printSuccess("Пользователь зарегистрирован.");
    }

    @Test
    void registerUser_ThrowsException() {
        UserIn userIn = new UserIn(
                ID,
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER
        );
        doThrow(new UserAlreadyExistsException("john@example.com"))
                .when(userService).registerUser(userIn);

        userController.registerUser(userIn);

        verify(printer, times(1)).printError("Пользователь с email john@example.com уже существует.");
    }

    @Test
    void getUserById_Success() {
        UserOut userOut = new UserOut(
                ID,
                "John Doe",
                "john@example.com",
                UserRole.USER,
                false
        );
        when(userService.getUserById(ID)).thenReturn(userOut);

        UserOut result = userController.getUserById(ID);

        assertEquals(userOut, result);
    }

    @Test
    void getUserById_ThrowsException() {
        when(userService.getUserById(ID)).thenThrow(new UserNotFoundException());

        UserOut result = userController.getUserById(ID);

        assertNull(result);
        verify(printer, times(1)).printError("Пользователь не найден.");
    }

    @Test
    void getUserByEmail_Success() {
        UserOut userOut = new UserOut(
                ID,
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
        when(userService.getUserByEmail("john@example.com")).thenThrow(new UserNotFoundException("john@example.com"));

        UserOut result = userController.getUserByEmail("john@example.com");

        assertNull(result);
        verify(printer, times(1)).printError("Пользователь с email: john@example.com не найден.");
    }

    @Test
    void updateUser_Success() {
        UserIn userIn = new UserIn(
                ID,
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
    void updateUser_ThrowsException() {
        UserIn userIn = new UserIn(
                ID,
                "John Doe Updated",
                "john@example.com",
                "newpassword123",
                UserRole.USER
        );
        doThrow(new UserNotFoundException("john@example.com")).when(userService).updateUser("john@example.com", userIn);

        userController.updateUser("john@example.com", userIn);

        verify(printer, times(1)).printError("Пользователь с email: john@example.com не найден.");
    }

    @Test
    void deleteUserByEmail_Success() {
        doNothing().when(userService).deleteUserEmail("john@example.com");

        userController.deleteUserByEmail("john@example.com");

        verify(userService, times(1)).deleteUserEmail("john@example.com");
        verify(printer, times(1)).printSuccess("Аккаунт успешно удален.");
    }

    @Test
    void deleteUserByEmail_ThrowsException() {
        doThrow(new UserNotFoundException("john@example.com")).when(userService).deleteUserEmail("john@example.com");

        userController.deleteUserByEmail("john@example.com");

        verify(printer, times(1)).printError("Пользователь с email: john@example.com не найден.");
    }
}