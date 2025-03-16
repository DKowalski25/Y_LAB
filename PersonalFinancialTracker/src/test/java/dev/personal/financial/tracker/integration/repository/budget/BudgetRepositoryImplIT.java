package dev.personal.financial.tracker.integration.repository.budget;

import dev.personal.financial.tracker.data.ModelFactory;
import dev.personal.financial.tracker.exception.budget.BudgetNotFoundException;
import dev.personal.financial.tracker.model.Budget;
import dev.personal.financial.tracker.repository.budget.BudgetRepository;
import dev.personal.financial.tracker.repository.budget.BudgetRepositoryImpl;
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
public class BudgetRepositoryImplIT {

    private static BudgetRepository budgetRepository;

    @BeforeAll
    static void setUp() throws SQLException {
        PostgresTestContainer.start();
        PostgresTestContainer.setUpDatabase();

        PostgresTestContainer.createBudgetTable();

        budgetRepository = new BudgetRepositoryImpl(PostgresTestContainer.getConnection());
    }

    @AfterAll
    static void tearDown() throws SQLException {
        PostgresTestContainer.tearDownDatabase();
        PostgresTestContainer.stop();
    }

    @AfterEach
    void cleanUp() throws SQLException {
        PostgresTestContainer.executeSql("DELETE FROM budgets");
    }

    @Test
    void testSaveAndFindByUserId() {
        Budget budget = ModelFactory.createBudget();

        budgetRepository.save(budget);

        Budget foundBudget = budgetRepository.findByUserId(budget.getUserId());

        assertNotNull(foundBudget);
        assertEquals(budget.getUserId(), foundBudget.getUserId());
        assertEquals(budget.getMonthlyBudget(), foundBudget.getMonthlyBudget());
    }

    @Test
    void testUpdate() {
        Budget budget = ModelFactory.createBudget();

        budgetRepository.save(budget);

        budget.setMonthlyBudget(new BigDecimal("2000.00"));
        budgetRepository.update(budget);

        Budget updatedBudget = budgetRepository.findByUserId(budget.getUserId());

        assertEquals(new BigDecimal("2000.00"), updatedBudget.getMonthlyBudget());
    }

    @Test
    void testDelete() {
        Budget budget = ModelFactory.createBudget();

        budgetRepository.save(budget);

        budgetRepository.delete(budget.getUserId());

        assertThrows(BudgetNotFoundException.class, () -> budgetRepository.findByUserId(budget.getUserId()));
    }
}
