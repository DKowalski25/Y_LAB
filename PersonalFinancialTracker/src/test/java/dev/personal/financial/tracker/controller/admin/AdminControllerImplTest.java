package dev.personal.financial.tracker.controller.admin;

import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.UserRole;
import dev.personal.financial.tracker.service.admin.AdminService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AdminControllerImplTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminControllerImpl adminController;

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
    void getAllUsers_Success() {
        List<UserOut> users = Collections.singletonList(
                new UserOut(
                        "1",
                        "John Doe",
                        "john@example.com",
                        UserRole.USER,
                        false
                )
        );
        when(adminService.getAllUsers()).thenReturn(users);

        List<UserOut> result = adminController.getAllUsers();

        assertEquals(users, result);
    }

    @Test
    void blockUser_Success() {
        doNothing().when(adminService).blockUser("1");

        adminController.blockUser("1");

        verify(adminService, times(1)).blockUser("1");
    }

    @Test
    void deleteUser_Success() {
        doNothing().when(adminService).deleteUser("1");

        adminController.deleteUser("1");

        verify(adminService, times(1)).deleteUser("1");
    }
}