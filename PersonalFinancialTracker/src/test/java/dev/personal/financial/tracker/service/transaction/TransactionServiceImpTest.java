package dev.personal.financial.tracker.service.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.model.Transaction;
import dev.personal.financial.tracker.repository.transaction.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

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
    void addTransaction_ShouldSaveTransaction() {
        TransactionIn transactionIn = new TransactionIn(
                "user1",
                100.0,
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                "user1",
                100.0,
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );

        doNothing().when(transactionRepository).save(any(Transaction.class));

        transactionService.addTransaction(transactionIn);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void getTransactionById_ShouldReturnTransactionOut_WhenTransactionExists() {
        String transactionId = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(
                transactionId,
                "user1",
                100.0,
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );
        when(transactionRepository.findById(transactionId)).thenReturn(transaction);

        TransactionOut result = transactionService.getTransactionById(transactionId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(transactionId);
        assertThat(result.getUserId()).isEqualTo("user1");
        assertThat(result.getAmount()).isEqualTo(100.0);
        assertThat(result.getCategory()).isEqualTo("Food");
        assertThat(result.getDescription()).isEqualTo("Lunch");
        assertThat(result.isIncome()).isTrue();
        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    void getTransactionsByUserId_ShouldReturnListOfTransactionOut() {
        String userId = "user1";
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                userId,
                100.0,
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );
        when(transactionRepository.findByUserId(userId)).thenReturn(Collections.singletonList(transaction));

        List<TransactionOut> result = transactionService.getTransactionsByUserId(userId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(userId);
        verify(transactionRepository, times(1)).findByUserId(userId);
    }

    @Test
    void updateTransaction_ShouldUpdateTransaction_WhenTransactionExists() {
        String transactionId = UUID.randomUUID().toString();
        TransactionIn transactionIn = new TransactionIn(
                "user1",
                200.0,
                "Food",
                LocalDate.now(),
                "Dinner",
                true
        );
        Transaction existingTransaction = new Transaction(
                transactionId,
                "user1",
                100.0,
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );

        when(transactionRepository.findById(transactionId)).thenReturn(existingTransaction);
        doNothing().when(transactionRepository).save(any(Transaction.class));

        transactionService.updateTransaction(transactionId, transactionIn);

        verify(transactionRepository, times(1)).findById(transactionId);
        verify(transactionRepository, times(1)).save(existingTransaction);
        assertThat(existingTransaction.getAmount()).isEqualTo(200.0);
        assertThat(existingTransaction.getCategory()).isEqualTo("Food");
        assertThat(existingTransaction.getDescription()).isEqualTo("Dinner");
        assertThat(existingTransaction.isIncome()).isTrue();
    }

    @Test
    void updateTransaction_ShouldThrowException_WhenTransactionDoesNotExist() {
        String transactionId = UUID.randomUUID().toString();
        TransactionIn transactionIn = new TransactionIn(
                "user1", 200.0, "Food", LocalDate.now(), "Dinner", true);

        when(transactionRepository.findById(transactionId)).thenReturn(null);

        assertThatThrownBy(() -> transactionService.updateTransaction(transactionId, transactionIn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Транзакция с id " + transactionId + " не найдена");

        verify(transactionRepository, times(1)).findById(transactionId);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void deleteTransaction_ShouldDeleteTransaction_WhenTransactionExists() {
        String transactionId = UUID.randomUUID().toString();
        when(transactionRepository.findById(transactionId)).thenReturn(new Transaction(
                transactionId,
                "user1",
                100.0,
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        ));
        doNothing().when(transactionRepository).delete(transactionId);

        transactionService.deleteTransaction(transactionId);

        verify(transactionRepository, times(1)).delete(transactionId);
    }

    @Test
    void getTransactionsByUserIdAndCategory_ShouldReturnFilteredTransactions() {
        String userId = "user1";
        String category = "Food";
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                userId,
                100.0,
                category,
                LocalDate.now(),
                "Lunch",
                true
        );
        when(transactionRepository.findByUserIdAndCategory(userId, category)).thenReturn(Collections.singletonList(transaction));

        List<TransactionOut> result = transactionService.getTransactionsByUserIdAndCategory(userId, category);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo(category);
        verify(transactionRepository, times(1)).findByUserIdAndCategory(userId, category);
    }

    @Test
    void getTransactionsByUserIdAndDate_ShouldReturnFilteredTransactions() {
        String userId = "user1";
        LocalDate date = LocalDate.now();
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                userId,
                100.0,
                "Food",
                date,
                "Lunch",
                true
        );
        when(transactionRepository.findByUserIdAndDate(userId, date)).thenReturn(Collections.singletonList(transaction));

        List<TransactionOut> result = transactionService.getTransactionsByUserIdAndDate(userId, date);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDate()).isEqualTo(date);
        verify(transactionRepository, times(1)).findByUserIdAndDate(userId, date);
    }

    @Test
    void getTransactionsByUserIdAndType_ShouldReturnFilteredTransactions() {
        String userId = "user1";
        boolean isIncome = true;
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                userId,
                100.0,
                "Food",
                LocalDate.now(),
                "Lunch",
                isIncome
        );
        when(transactionRepository.findByUserIdAndType(userId, isIncome)).thenReturn(Collections.singletonList(transaction));

        List<TransactionOut> result = transactionService.getTransactionsByUserIdAndType(userId, isIncome);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).isIncome()).isTrue();
        verify(transactionRepository, times(1)).findByUserIdAndType(userId, isIncome);
    }

    @Test
    void getTotalExpensesForCurrentMonth_ShouldReturnTotalExpenses() {
        String userId = "user1";
        when(transactionRepository.getTotalExpensesForCurrentMonth(userId)).thenReturn(500.0);

        double result = transactionService.getTotalExpensesForCurrentMonth(userId);

        assertThat(result).isEqualTo(500.0);
        verify(transactionRepository, times(1)).getTotalExpensesForCurrentMonth(userId);
    }

    @Test
    void getTransactionsByUserIdAndDateRange_ShouldReturnFilteredTransactions() {
        String userId = "user1";
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                userId,
                100.0,
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );
        when(transactionRepository.findByUserIdAndDateRange(userId, startDate, endDate))
                .thenReturn(Collections.singletonList(transaction));

        List<TransactionOut> result = transactionService
                .getTransactionsByUserIdAndDateRange(userId, startDate, endDate);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDate()).isBetween(startDate, endDate);
        verify(transactionRepository, times(1))
                .findByUserIdAndDateRange(userId, startDate, endDate);
    }
}