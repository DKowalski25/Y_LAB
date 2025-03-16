package dev.personal.financial.tracker.integration.repository.admin;

import dev.personal.financial.tracker.data.ModelFactory;
import dev.personal.financial.tracker.exception.user.UserAlreadyBlockedException;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.model.UserRole;
import dev.personal.financial.tracker.repository.admin.AdminRepositoryImpl;
import dev.personal.financial.tracker.repository.user.UserRepository;
import dev.personal.financial.tracker.repository.user.UserRepositoryImpl;
import dev.personal.financial.tracker.util.PostgresTestContainer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.testcontainers.junit.jupiter.Testcontainers;


import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class AdminRepositoryImplTest {

    private AdminRepositoryImpl adminRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws SQLException {
        PostgresTestContainer.start();
        PostgresTestContainer.setUpDatabase();
        PostgresTestContainer.createUserTable();


        userRepository = new UserRepositoryImpl(PostgresTestContainer.getConnection());
        adminRepository = new AdminRepositoryImpl(PostgresTestContainer.getConnection());
    }

    @AfterEach
    void tearDown() throws SQLException {
        PostgresTestContainer.cleanUpDatabase();
        PostgresTestContainer.tearDownDatabase();
        PostgresTestContainer.stop();
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() throws SQLException {
        User user1 = ModelFactory.createUser(1, "User1", "user1@example.com", "password", UserRole.USER, false);
        User user2 = ModelFactory.createUser(2, "User2", "user2@example.com", "password", UserRole.ADMIN, true);
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = adminRepository.getAllUsers();

        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getId() == 1 && u.getName().equals("User1")));
        assertTrue(users.stream().anyMatch(u -> u.getId() == 2 && u.getName().equals("User2")));
    }

    @Test
    void blockUser_ShouldBlockUser() throws SQLException, UserNotFoundException, UserAlreadyBlockedException {
        User user = ModelFactory.createUser(1, "User1", "user1@example.com", "password", UserRole.USER, false);
        userRepository.save(user);

        adminRepository.blockUser(1);

        User blockedUser = userRepository.findById(1);
        assertTrue(blockedUser.getIsBlocked());
    }

    @Test
    void blockUser_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        assertThrows(UserNotFoundException.class, () -> adminRepository.blockUser(999));
    }

    @Test
    void deleteUser_ShouldDeleteUser() throws SQLException, UserNotFoundException {
        User user = ModelFactory.createUser(1, "User1", "user1@example.com", "password", UserRole.USER, false);
        userRepository.save(user);

        adminRepository.deleteUser(1);

        assertThrows(UserNotFoundException.class, () -> userRepository.findById(1));
    }

    @Test
    void deleteUser_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        assertThrows(UserNotFoundException.class, () -> adminRepository.deleteUser(999));
    }
}