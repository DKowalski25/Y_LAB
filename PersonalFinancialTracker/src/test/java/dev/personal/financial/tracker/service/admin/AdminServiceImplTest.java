package dev.personal.financial.tracker.service.admin;

import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.model.UserRole;
import dev.personal.financial.tracker.repository.admin.AdminRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

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
    void getAllUsers_ShouldReturnListOfUserOut() {
        User user1 = new User(
                "1",
                "John Doe",
                "john@example.com",
                "password123",
                UserRole.USER,
                false
        );
        User user2 = new User(
                "2",
                "admin",
                "admin@example.com",
                "password456",
                UserRole.ADMIN,
                true
        );
        when(adminRepository.getAllUsers()).thenReturn(List.of(user1, user2));

        List<UserOut> result = adminService.getAllUsers();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo("1");
        assertThat(result.get(0).getName()).isEqualTo("John Doe");
        assertThat(result.get(0).getEmail()).isEqualTo("john@example.com");
        assertThat(result.get(0).getRole()).isEqualTo(UserRole.USER);
        assertThat(result.get(0).getIsBlocked()).isFalse();

        assertThat(result.get(1).getId()).isEqualTo("2");
        assertThat(result.get(1).getName()).isEqualTo("admin");
        assertThat(result.get(1).getEmail()).isEqualTo("admin@example.com");
        assertThat(result.get(1).getRole()).isEqualTo(UserRole.ADMIN);
        assertThat(result.get(1).getIsBlocked()).isTrue();

        verify(adminRepository, times(1)).getAllUsers();
    }

    @Test
    void blockUser_ShouldBlockUser() {
        String userId = "1";
        doNothing().when(adminRepository).blockUser(userId);

        adminService.blockUser(userId);

        verify(adminRepository, times(1)).blockUser(userId);
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        String userId = "1";
        doNothing().when(adminRepository).deleteUser(userId);

        adminService.deleteUser(userId);

        verify(adminRepository, times(1)).deleteUser(userId);
    }
}