package dev.personal.financial.tracker.controller.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.service.transaction.TransactionService;
import dev.personal.financial.tracker.util.ConsolePrinter;

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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TransactionControllerImplTest {

    private final static int ID = UUID.randomUUID().hashCode();
    private final static int USER_ID = UUID.randomUUID().hashCode();

    @Mock
    private TransactionService transactionService;

    @Mock
    private ConsolePrinter printer;

    @InjectMocks
    private TransactionControllerImpl transactionController;

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
    void addTransaction_Success() {
        TransactionIn transactionIn = new TransactionIn(
                ID,
                BigDecimal.valueOf(100.0),
                "Food",
                LocalDate.now(),
                "Lunch",
                false
        );
        doNothing().when(transactionService).addTransaction(transactionIn);

        transactionController.addTransaction(transactionIn);

        verify(transactionService, times(1)).addTransaction(transactionIn);
    }

    @Test
    void getTransaction_Success() {
        TransactionOut transactionOut = new TransactionOut(
                ID,
                USER_ID,
                BigDecimal.valueOf(100.0),
                "Food",
                LocalDate.now(),
                "Lunch",
                false
        );
        when(transactionService.getTransactionById(ID)).thenReturn(transactionOut);

        TransactionOut result = transactionController.getTransaction(ID);

        assertEquals(transactionOut, result);
    }

    @Test
    void getTransactionsByUserId_Success() {
        List<TransactionOut> transactions = Collections.singletonList(
                new TransactionOut(
                        ID,
                        USER_ID,
                        BigDecimal.valueOf(100.0),
                        "Food",
                        LocalDate.now(),
                        "Lunch",
                        false
                )
        );
        when(transactionService.getTransactionsByUserId(USER_ID)).thenReturn(transactions);

        List<TransactionOut> result = transactionController.getTransactionsByUserId(USER_ID);

        assertEquals(transactions, result);
    }

    @Test
    void updateTransaction_Success() {
        TransactionIn transactionIn = new TransactionIn(
                ID,
                BigDecimal.valueOf( 150.0),
                "Food",
                LocalDate.now(),
                "Dinner",
                false
        );
        doNothing().when(transactionService).updateTransaction(ID, transactionIn);

        transactionController.updateTransaction(ID, transactionIn);

        verify(transactionService, times(1)).updateTransaction(ID, transactionIn);
        verify(printer, times(1)).printSuccess("Транзакция успешно обновлена.");
    }

    @Test
    void deleteTransaction_Success() {
        doNothing().when(transactionService).deleteTransaction(ID);

        transactionController.deleteTransaction(ID);

        verify(transactionService, times(1)).deleteTransaction(ID);
    }

    @Test
    void getTransactionsByUserIdAndCategory_Success() {
        List<TransactionOut> transactions = Collections.singletonList(
                new TransactionOut(
                        ID,
                        USER_ID,
                        BigDecimal.valueOf(100.0),
                        "Food",
                        LocalDate.now(),
                        "Lunch",
                        false
                )
        );
        when(transactionService.getTransactionsByUserIdAndCategory(USER_ID, "Food"))
                .thenReturn(transactions);

        List<TransactionOut> result = transactionController
                .getTransactionsByUserIdAndCategory(USER_ID, "Food");

        assertEquals(transactions, result);
    }

    @Test
    void getTransactionsByUserIdAndDate_Success() {
        List<TransactionOut> transactions = Collections.singletonList(
                new TransactionOut(
                        ID,
                        USER_ID,
                        BigDecimal.valueOf(100.0),
                        "Food",
                        LocalDate.now(),
                        "Lunch",
                        false
                )
        );
        LocalDate date = LocalDate.now();
        when(transactionService.getTransactionsByUserIdAndDate(USER_ID, date)).thenReturn(transactions);

        List<TransactionOut> result = transactionController.getTransactionsByUserIdAndDate(USER_ID, date);

        assertEquals(transactions, result);
    }

    @Test
    void getTransactionsByUserIdAndType_Success() {
        List<TransactionOut> transactions = Collections.singletonList(
                new TransactionOut(
                        ID,
                        USER_ID,
                        BigDecimal.valueOf(100.0),
                        "Food",
                        LocalDate.now(),
                        "Lunch",
                        false
                )
        );
        when(transactionService.getTransactionsByUserIdAndType(USER_ID, false)).thenReturn(transactions);

        List<TransactionOut> result = transactionController.getTransactionsByUserIdAndType(USER_ID, false);

        assertEquals(transactions, result);
    }

    @Test
    void getTotalExpensesForCurrentMonth_Success() {
        when(transactionService.getTotalExpensesForCurrentMonth(USER_ID)).thenReturn(BigDecimal.valueOf(500.0));

        BigDecimal result = transactionController.getTotalExpensesForCurrentMonth(USER_ID);

        assertEquals(BigDecimal.valueOf(500.0), result);
    }

    @Test
    void getTransactionsByUserIdAndDateRange_Success() {
        List<TransactionOut> transactions = Collections.singletonList(
                new TransactionOut(
                        ID,
                        USER_ID,
                        BigDecimal.valueOf(100.0),
                        "Food",
                        LocalDate.now(),
                        "Lunch",
                        false
                )
        );
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        when(transactionService.getTransactionsByUserIdAndDateRange(USER_ID, startDate, endDate))
                .thenReturn(transactions);

        List<TransactionOut> result = transactionController
                .getTransactionsByUserIdAndDateRange(USER_ID, startDate, endDate);

        assertEquals(transactions, result);
    }
}