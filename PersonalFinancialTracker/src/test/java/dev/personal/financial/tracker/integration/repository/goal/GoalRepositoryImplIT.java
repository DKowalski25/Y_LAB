package dev.personal.financial.tracker.integration.repository.goal;

import dev.personal.financial.tracker.data.ModelFactory;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;
import dev.personal.financial.tracker.model.Goal;
import dev.personal.financial.tracker.repository.goal.GoalRepository;
import dev.personal.financial.tracker.repository.goal.GoalRepositoryImpl;
import dev.personal.financial.tracker.util.PostgresTestContainer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class GoalRepositoryImplIT {

    private static GoalRepository goalRepository;

    @BeforeAll
    static void setUp() throws SQLException {
        PostgresTestContainer.start();
        PostgresTestContainer.setUpDatabase();

        PostgresTestContainer.createGoalTable();

        goalRepository = new GoalRepositoryImpl(PostgresTestContainer.getConnection());
    }

    @AfterAll
    static void tearDown() throws SQLException {
        PostgresTestContainer.tearDownDatabase();
        PostgresTestContainer.stop();
    }

    @AfterEach
    void cleanUp() throws SQLException {
        PostgresTestContainer.executeSql("DELETE FROM app.goals");
    }

    @Test
    void testSaveAndFindById() {
        Goal goal = ModelFactory.createGoal();

        goalRepository.save(goal);

        Goal foundGoal = goalRepository.findById(goal.getId());

        assertNotNull(foundGoal);
        assertEquals(goal.getUserId(), foundGoal.getUserId());
        assertEquals(goal.getGoalName(), foundGoal.getGoalName());
        assertEquals(goal.getGoalAmount(), foundGoal.getGoalAmount());
        assertEquals(goal.getCurrentAmount(), foundGoal.getCurrentAmount());
        assertEquals(goal.getSavedAmount(), foundGoal.getSavedAmount());
    }

    @Test
    void testFindByUserId() {
        Goal goal = ModelFactory.createGoal();

        goalRepository.save(goal);

        Goal foundGoal = goalRepository.findByUserId(goal.getUserId());

        assertNotNull(foundGoal);
        assertEquals(goal.getId(), foundGoal.getId());
    }

    @Test
    void testUpdate() {
        Goal goal = ModelFactory.createGoal();

        goalRepository.save(goal);

        goal.setGoalName("Updated Goal Name");
        goal.setGoalAmount(new BigDecimal("2000.0"));
        goalRepository.update(goal);

        Goal updatedGoal = goalRepository.findById(goal.getId());

        assertEquals("Updated Goal Name", updatedGoal.getGoalName());
        assertEquals(new BigDecimal("2000.00"), updatedGoal.getGoalAmount());
    }

    @Test
    void testDeleteByUserId() {
        Goal goal = ModelFactory.createGoal();

        goalRepository.save(goal);

        goalRepository.deleteByUserId(goal.getUserId());

        assertThrows(GoalNotFoundException.class, () -> goalRepository.findById(goal.getId()));
    }

    @Test
    void testUpdateSavedAmount() {
        Goal goal = ModelFactory.createGoal();

        goalRepository.save(goal);

        BigDecimal amountToAdd = BigDecimal.valueOf(500.0);
        goalRepository.updateSavedAmount(goal.getId(), amountToAdd);

        BigDecimal updatedSavedAmount = goalRepository.getSavedAmount(goal.getId());

        assertEquals(goal.getSavedAmount().add(amountToAdd), updatedSavedAmount);
    }

    @Test
    void testGetSavedAmount() {
        Goal goal = ModelFactory.createGoal();

        goalRepository.save(goal);

        BigDecimal savedAmount = goalRepository.getSavedAmount(goal.getId());

        assertEquals(goal.getSavedAmount(), savedAmount);
    }
}
