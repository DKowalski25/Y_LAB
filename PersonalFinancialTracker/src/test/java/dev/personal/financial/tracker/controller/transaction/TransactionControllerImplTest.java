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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TransactionControllerImplTest {

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
                "1",
                100.0,
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
                "1",
                "1",
                100.0,
                "Food",
                LocalDate.now(),
                "Lunch",
                false
        );
        when(transactionService.getTransactionById("1")).thenReturn(transactionOut);

        TransactionOut result = transactionController.getTransaction("1");

        assertEquals(transactionOut, result);
    }

    @Test
    void getTransactionsByUserId_Success() {
        List<TransactionOut> transactions = Collections.singletonList(
                new TransactionOut(
                        "1",
                        "1",
                        100.0,
                        "Food",
                        LocalDate.now(),
                        "Lunch",
                        false
                )
        );
        when(transactionService.getTransactionsByUserId("1")).thenReturn(transactions);

        List<TransactionOut> result = transactionController.getTransactionsByUserId("1");

        assertEquals(transactions, result);
    }

    @Test
    void updateTransaction_Success() {
        TransactionIn transactionIn = new TransactionIn(
                "1",
                150.0,
                "Food",
                LocalDate.now(),
                "Dinner",
                false
        );
        doNothing().when(transactionService).updateTransaction("1", transactionIn);

        transactionController.updateTransaction("1", transactionIn);

        verify(transactionService, times(1)).updateTransaction("1", transactionIn);
        verify(printer, times(1)).printSuccess("Транзакция успешно обновлена.");
    }

    @Test
    void deleteTransaction_Success() {
        doNothing().when(transactionService).deleteTransaction("1");

        transactionController.deleteTransaction("1");

        verify(transactionService, times(1)).deleteTransaction("1");
    }

    @Test
    void getTransactionsByUserIdAndCategory_Success() {
        List<TransactionOut> transactions = Collections.singletonList(
                new TransactionOut(
                        "1",
                        "1",
                        100.0,
                        "Food",
                        LocalDate.now(),
                        "Lunch",
                        false
                )
        );
        when(transactionService.getTransactionsByUserIdAndCategory("1", "Food"))
                .thenReturn(transactions);

        List<TransactionOut> result = transactionController
                .getTransactionsByUserIdAndCategory("1", "Food");

        assertEquals(transactions, result);
    }

    @Test
    void getTransactionsByUserIdAndDate_Success() {
        List<TransactionOut> transactions = Collections.singletonList(
                new TransactionOut(
                        "1",
                        "1",
                        100.0,
                        "Food",
                        LocalDate.now(),
                        "Lunch",
                        false
                )
        );
        LocalDate date = LocalDate.now();
        when(transactionService.getTransactionsByUserIdAndDate("1", date)).thenReturn(transactions);

        List<TransactionOut> result = transactionController.getTransactionsByUserIdAndDate("1", date);

        assertEquals(transactions, result);
    }

    @Test
    void getTransactionsByUserIdAndType_Success() {
        List<TransactionOut> transactions = Collections.singletonList(
                new TransactionOut(
                        "1",
                        "1",
                        100.0,
                        "Food",
                        LocalDate.now(),
                        "Lunch",
                        false
                )
        );
        when(transactionService.getTransactionsByUserIdAndType("1", false)).thenReturn(transactions);

        List<TransactionOut> result = transactionController.getTransactionsByUserIdAndType("1", false);

        assertEquals(transactions, result);
    }

    @Test
    void getTotalExpensesForCurrentMonth_Success() {
        when(transactionService.getTotalExpensesForCurrentMonth("1")).thenReturn(500.0);

        double result = transactionController.getTotalExpensesForCurrentMonth("1");

        assertEquals(500.0, result);
    }

    @Test
    void getTransactionsByUserIdAndDateRange_Success() {
        List<TransactionOut> transactions = Collections.singletonList(
                new TransactionOut(
                        "1",
                        "1",
                        100.0,
                        "Food",
                        LocalDate.now(),
                        "Lunch",
                        false
                )
        );
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        when(transactionService.getTransactionsByUserIdAndDateRange("1", startDate, endDate))
                .thenReturn(transactions);

        List<TransactionOut> result = transactionController
                .getTransactionsByUserIdAndDateRange("1", startDate, endDate);

        assertEquals(transactions, result);
    }
}