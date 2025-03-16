package dev.personal.financial.tracker.integration.repository.user;

import dev.personal.financial.tracker.data.ModelFactory;
import dev.personal.financial.tracker.exception.user.UserNotFoundException;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.model.UserRole;
import dev.personal.financial.tracker.repository.user.UserRepository;
import dev.personal.financial.tracker.repository.user.UserRepositoryImpl;

import dev.personal.financial.tracker.util.PostgresTestContainer;
import org.junit.jupiter.api.*;

import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class UserRepositoryImplIT {

    private static UserRepository userRepository;

    @BeforeAll
    static void setUp() throws SQLException {
        PostgresTestContainer.start();
        PostgresTestContainer.setUpDatabase();
        PostgresTestContainer.createUserTable();
        userRepository = new UserRepositoryImpl(PostgresTestContainer.getConnection());
    }

    @AfterAll
    static void tearDown() throws SQLException {
        PostgresTestContainer.tearDownDatabase();
        PostgresTestContainer.stop();
    }

    @AfterEach
    void cleanUp() throws SQLException {
        PostgresTestContainer.cleanUpDatabase();
    }

    @Test
    void testSaveAndFindById() {
        User user = ModelFactory.createUser();



        userRepository.save(user);

        User foundUser = userRepository.findById(user.getId());

        assertNotNull(foundUser);
        assertEquals(user.getName(), foundUser.getName());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getPassword(), foundUser.getPassword());
        assertEquals(user.getRole(), foundUser.getRole());
        assertEquals(user.getIsBlocked(), foundUser.getIsBlocked());
    }

    @Test
    void testFindAll() {
        User user1 = ModelFactory.createUser(0, "John Doe", "john@example.com", "password", UserRole.USER, false);
        User user2 = ModelFactory.createUser(0, "Jane Doe", "jane@example.com", "password", UserRole.ADMIN, false);

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals("john@example.com")));
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals("jane@example.com")));
    }

    @Test
    void testGetByEmail() {
        User user = ModelFactory.createUser();

        userRepository.save(user);

        User foundUser = userRepository.getByEmail("john@example.com");

        assertNotNull(foundUser);
        assertEquals(user.getName(), foundUser.getName());
    }

    @Test
    void testUpdate() {
        User user = ModelFactory.createUser();

        userRepository.save(user);

        user.setName("Jane Doe");
        user.setEmail("jane@example.com");
        userRepository.update(user);

        User updatedUser = userRepository.findById(user.getId());

        assertEquals("Jane Doe", updatedUser.getName());
        assertEquals("jane@example.com", updatedUser.getEmail());
    }

    @Test
    void testDelete() {
        User user = ModelFactory.createUser();

        userRepository.save(user);

        userRepository.delete(user.getId());

        assertThrows(UserNotFoundException.class, () -> userRepository.findById(user.getId()));
    }

    @Test
    void testExistsByEmail() {
        User user = ModelFactory.createUser();

        userRepository.save(user);

        assertTrue(userRepository.existsByEmail("john@example.com"));
        assertFalse(userRepository.existsByEmail("nonexistent@example.com"));
    }
}
