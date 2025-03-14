package dev.personal.financial.tracker.service.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.exception.transaction.TransactionNotFoundException;
import dev.personal.financial.tracker.model.Transaction;
import dev.personal.financial.tracker.repository.transaction.TransactionRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    private final static int ID = UUID.randomUUID().hashCode();
    private final static int USER_ID = UUID.randomUUID().hashCode();

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
                USER_ID,
                BigDecimal.valueOf(100.0),
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );

        transactionService.addTransaction(transactionIn);

        verify(transactionRepository, times(1)).save(argThat(transaction ->
                transaction.getUserId() == (USER_ID) &&
                        transaction.getAmount().compareTo(BigDecimal.valueOf(100.0)) == 0 &&
                        transaction.getCategory().equals("Food") &&
                        transaction.getDate().equals(LocalDate.now()) &&
                        transaction.getDescription().equals("Lunch") &&
                        transaction.isIncome()
        ));
    }

    @Test
    void getTransactionById_ShouldReturnTransactionOut_WhenTransactionExists() {
        Transaction transaction = new Transaction(
                ID,
                USER_ID,
                BigDecimal.valueOf(100.0),
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );
        when(transactionRepository.findById(ID)).thenReturn(transaction);

        TransactionOut result = transactionService.getTransactionById(ID);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(100.0));
        assertThat(result.getCategory()).isEqualTo("Food");
        assertThat(result.getDescription()).isEqualTo("Lunch");
        assertThat(result.isIncome()).isTrue();
        verify(transactionRepository, times(1)).findById(ID);
    }

    @Test
    void getTransactionById_ShouldThrowException_WhenTransactionDoesNotExist() {
        when(transactionRepository.findById(ID)).thenThrow(new TransactionNotFoundException(ID));

        assertThatThrownBy(() -> transactionService.getTransactionById(ID))
                .isInstanceOf(TransactionNotFoundException.class)
                .hasMessage("Транзакция с id: " + ID + " не найдена.");

        verify(transactionRepository, times(1)).findById(ID);
    }

    @Test
    void getTransactionsByUserId_ShouldReturnListOfTransactionOut() {
        Transaction transaction = new Transaction(
                ID,
                USER_ID,
                BigDecimal.valueOf(100.0),
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );
        when(transactionRepository.findByUserId(ID)).thenReturn(Collections.singletonList(transaction));

        List<TransactionOut> result = transactionService.getTransactionsByUserId(ID);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(USER_ID);
        verify(transactionRepository, times(1)).findByUserId(ID);
    }

    @Test
    void updateTransaction_ShouldUpdateTransaction_WhenTransactionExists() {
        String transactionId = UUID.randomUUID().toString();
        TransactionIn transactionIn = new TransactionIn(
                USER_ID,
                BigDecimal.valueOf(200.0),
                "Food",
                LocalDate.now(),
                "Dinner",
                true
        );
        Transaction existingTransaction = new Transaction(
                ID,
                USER_ID,
                BigDecimal.valueOf(100.0),
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );

        when(transactionRepository.findById(ID)).thenReturn(existingTransaction);

        transactionService.updateTransaction(ID, transactionIn);

        verify(transactionRepository, times(1)).findById(ID);
        verify(transactionRepository, times(1)).update(argThat(transaction ->
                transaction.getAmount().compareTo(BigDecimal.valueOf(200.0)) == 0 &&
                        transaction.getDescription().equals("Dinner")
        ));
    }

    @Test
    void updateTransaction_ShouldThrowException_WhenTransactionDoesNotExist() {
        TransactionIn transactionIn = new TransactionIn(
                USER_ID,
                BigDecimal.valueOf(200.0),
                "Food",
                LocalDate.now(),
                "Dinner",
                true
        );

        when(transactionRepository.findById(ID)).thenThrow(new TransactionNotFoundException(ID));

        assertThatThrownBy(() -> transactionService.updateTransaction(ID, transactionIn))
                .isInstanceOf(TransactionNotFoundException.class)
                .hasMessage("Транзакция с id: " + ID + " не найдена.");

        verify(transactionRepository, times(1)).findById(ID);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void deleteTransaction_ShouldDeleteTransaction_WhenTransactionExists() {
        when(transactionRepository.findById(ID)).thenReturn(new Transaction(
                ID,
                USER_ID,
                BigDecimal.valueOf(100.0),
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        ));
        doNothing().when(transactionRepository).delete(ID);

        transactionService.deleteTransaction(ID);

        verify(transactionRepository, times(1)).delete(ID);
    }

    @Test
    void deleteTransaction_ShouldThrowException_WhenTransactionDoesNotExist() {
        when(transactionRepository.findById(ID))
                .thenThrow(new TransactionNotFoundException(ID));

        assertThatThrownBy(() -> transactionService.deleteTransaction(ID))
                .isInstanceOf(TransactionNotFoundException.class)
                .hasMessage("Транзакция с id: " + ID + " не найдена.");

        verify(transactionRepository, times(1)).findById(ID);
        verify(transactionRepository, never()).delete(anyInt());
    }

    @Test
    void getTransactionsByUserIdAndCategory_ShouldReturnFilteredTransactions() {
        String userId = "user1";
        String category = "Food";
        Transaction transaction = new Transaction(
                ID,
                USER_ID,
                BigDecimal.valueOf(100.0),
                category,
                LocalDate.now(),
                "Lunch",
                true
        );
        when(transactionRepository.findByUserIdAndCategory(USER_ID, category)).thenReturn(Collections.singletonList(transaction));

        List<TransactionOut> result = transactionService.getTransactionsByUserIdAndCategory(USER_ID, category);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo(category);
        verify(transactionRepository, times(1)).findByUserIdAndCategory(USER_ID, category);
    }

    @Test
    void getTransactionsByUserIdAndDate_ShouldReturnFilteredTransactions() {
        String userId = "user1";
        LocalDate date = LocalDate.now();
        Transaction transaction = new Transaction(
                ID,
                USER_ID,
                BigDecimal.valueOf(100.0),
                "Food",
                date,
                "Lunch",
                true
        );
        when(transactionRepository.findByUserIdAndDate(USER_ID, date)).thenReturn(Collections.singletonList(transaction));

        List<TransactionOut> result = transactionService.getTransactionsByUserIdAndDate(USER_ID, date);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDate()).isEqualTo(date);
        verify(transactionRepository, times(1)).findByUserIdAndDate(USER_ID, date);
    }

    @Test
    void getTransactionsByUserIdAndType_ShouldReturnFilteredTransactions() {
        Transaction transaction = new Transaction(
                ID,
                USER_ID,
                BigDecimal.valueOf(100.0),
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );
        when(transactionRepository.findByUserIdAndType(USER_ID, true)).thenReturn(Collections.singletonList(transaction));

        List<TransactionOut> result = transactionService.getTransactionsByUserIdAndType(USER_ID, true);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).isIncome()).isTrue();
        verify(transactionRepository, times(1)).findByUserIdAndType(USER_ID, true);
    }

    @Test
    void getTotalExpensesForCurrentMonth_ShouldReturnTotalExpenses() {
        when(transactionRepository.getTotalExpensesForCurrentMonth(USER_ID)).thenReturn(BigDecimal.valueOf(500.0));

        BigDecimal result = transactionService.getTotalExpensesForCurrentMonth(USER_ID);

        assertThat(result).isEqualTo(BigDecimal.valueOf(500.0));
        verify(transactionRepository, times(1)).getTotalExpensesForCurrentMonth(USER_ID);
    }

    @Test
    void getTransactionsByUserIdAndDateRange_ShouldReturnFilteredTransactions() {
        String userId = "user1";
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();
        Transaction transaction = new Transaction(
                ID,
                USER_ID,
                BigDecimal.valueOf(100.0),
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );
        when(transactionRepository.findByUserIdAndDateRange(USER_ID, startDate, endDate))
                .thenReturn(Collections.singletonList(transaction));

        List<TransactionOut> result = transactionService
                .getTransactionsByUserIdAndDateRange(USER_ID, startDate, endDate);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDate()).isBetween(startDate, endDate);
        verify(transactionRepository, times(1))
                .findByUserIdAndDateRange(USER_ID, startDate, endDate);
    }
}