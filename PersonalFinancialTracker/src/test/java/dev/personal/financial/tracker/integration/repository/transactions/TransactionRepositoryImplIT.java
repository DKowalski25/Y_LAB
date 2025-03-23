package dev.personal.financial.tracker.integration.repository.transactions;

import dev.personal.financial.tracker.data.ModelFactory;
import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;
import dev.personal.financial.tracker.model.Transaction;
import dev.personal.financial.tracker.model.TransactionCategory;
import dev.personal.financial.tracker.repository.transaction.TransactionRepositoryImpl;
import dev.personal.financial.tracker.util.PostgresTestContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class TransactionRepositoryImplIT {

    private static TransactionRepositoryImpl transactionRepository;

    @BeforeAll
    static void setUp() throws SQLException {
        PostgresTestContainer.start();
        PostgresTestContainer.setUpDatabase();

        PostgresTestContainer.createTransactionTable();

        transactionRepository = new TransactionRepositoryImpl(PostgresTestContainer.getConnection());
    }

    @AfterAll
    static void tearDown() throws SQLException {
        PostgresTestContainer.tearDownDatabase();
        PostgresTestContainer.stop();
    }

    @AfterEach
    void cleanUp() throws SQLException {
        PostgresTestContainer.executeSql("DELETE FROM app.transactions");
    }

    @Test
    void testSaveAndFindById() {
        Transaction transaction = ModelFactory.createTransaction();

        transactionRepository.save(transaction);

        Transaction foundTransaction = transactionRepository.findById(transaction.getId());

        assertNotNull(foundTransaction);
        assertEquals(transaction.getUserId(), foundTransaction.getUserId());
        assertEquals(transaction.getAmount(), foundTransaction.getAmount());
        assertEquals(transaction.getCategory(), foundTransaction.getCategory());
        assertEquals(transaction.getDate(), foundTransaction.getDate());
        assertEquals(transaction.getDescription(), foundTransaction.getDescription());
        assertEquals(transaction.isIncome(), foundTransaction.isIncome());
    }

    @Test
    void testFindByUserId() {
        Transaction transaction = ModelFactory.createTransaction();

        transactionRepository.save(transaction);

        List<Transaction> transactions = transactionRepository.findByUserId(transaction.getUserId());

        assertEquals(1, transactions.size());
        assertEquals(transaction.getId(), transactions.get(0).getId());
    }

    @Test
    void testUpdate() {
        Transaction transaction = ModelFactory.createTransaction();

        transactionRepository.save(transaction);

        transaction.setAmount(new BigDecimal("200.00"));
        transaction.setCategory(TransactionCategory.TRANSPORT);
        transactionRepository.update(transaction);

        Transaction updatedTransaction = transactionRepository.findById(transaction.getId());

        assertEquals(new BigDecimal("200.00"), updatedTransaction.getAmount());
        assertEquals(TransactionCategory.TRANSPORT, updatedTransaction.getCategory());
    }

    @Test
    void testDelete() {
        Transaction transaction = ModelFactory.createTransaction();

        transactionRepository.save(transaction);

        transactionRepository.delete(transaction.getId());

        assertThrows(TransactionNotFoundException.class, () -> transactionRepository.findById(transaction.getId()));
    }

    @Test
    void testFindByUserIdAndCategory() {
        Transaction transaction = ModelFactory.createTransaction();

        transactionRepository.save(transaction);

        List<Transaction> transactions = transactionRepository.findByUserIdAndCategory(transaction.getUserId(), transaction.getCategory().toString());

        assertEquals(1, transactions.size());
        assertEquals(transaction.getId(), transactions.get(0).getId());
    }

    @Test
    void testFindByUserIdAndDate() {
        Transaction transaction = ModelFactory.createTransaction();

        transactionRepository.save(transaction);

        List<Transaction> transactions = transactionRepository.findByUserIdAndDate(transaction.getUserId(), transaction.getDate());

        assertEquals(1, transactions.size());
        assertEquals(transaction.getId(), transactions.get(0).getId());
    }

    @Test
    void testFindByUserIdAndType() {
        Transaction transaction = ModelFactory.createTransaction();

        transactionRepository.save(transaction);

        List<Transaction> transactions = transactionRepository.findByUserIdAndType(transaction.getUserId(), transaction.isIncome());

        assertEquals(1, transactions.size());
        assertEquals(transaction.getId(), transactions.get(0).getId());
    }

    @Test
    void testGetTotalExpensesForCurrentMonth() {
        Transaction transaction = new Transaction(
                0,
                1,
                new BigDecimal("100.00"),
                TransactionCategory.FOOD,
                LocalDate.now(),
                "Lunch",
                false
        );

        transactionRepository.save(transaction);

        BigDecimal totalExpenses = transactionRepository.getTotalExpensesForCurrentMonth(transaction.getUserId());

        assertEquals(new BigDecimal("100.00"), totalExpenses);
    }

    @Test
    void testFindByUserIdAndDateRange() {
        Transaction transaction = ModelFactory.createTransaction();

        transactionRepository.save(transaction);

        List<Transaction> transactions = transactionRepository.findByUserIdAndDateRange(
                transaction.getUserId(),
                transaction.getDate().minusDays(1),
                transaction.getDate().plusDays(1)
        );

        assertEquals(1, transactions.size());
        assertEquals(transaction.getId(), transactions.get(0).getId());
    }


}
