package dev.personal.financial.tracker.unit.util;

import dev.personal.financial.tracker.util.ConsolePrinter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ConsolePrinterTest {

    @Mock
    private Scanner sc;

    @InjectMocks
    private ConsolePrinter consolePrinter;

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
    void printPrompt_ShouldPrintMessage() {
        consolePrinter.printPrompt("Test prompt message");
    }

    @Test
    void printSuccess_ShouldPrintSuccessMessage() {
        consolePrinter.printSuccess("Test success message");
    }

    @Test
    void printError_ShouldPrintErrorMessage() {
        consolePrinter.printError("Test error message");
    }

    @Test
    void printInfo_ShouldPrintInfoMessage() {
        consolePrinter.printInfo("Test info message");
    }

    @Test
    void printWithDivider_ShouldPrintMessageWithDivider() {
        consolePrinter.printWithDivider("Test message with divider");
    }

    @Test
    void readNonEmptyString_ShouldReturnValidInput() {
        when(sc.nextLine()).thenReturn("valid input");
        String result = consolePrinter.readNonEmptyString("Enter something:");
        assertEquals("valid input", result);
    }

    @Test
    void readNonEmptyString_ShouldHandleEmptyInput() {
        when(sc.nextLine()).thenReturn("", "valid input");
        String result = consolePrinter.readNonEmptyString("Enter something:");
        assertEquals("valid input", result);
    }

    @Test
    void readNonEmptyString_ShouldHandleQuitCommand() {
        when(sc.nextLine()).thenReturn("q");
        String result = consolePrinter.readNonEmptyString("Enter something:");
        assertNull(result);
    }

    @Test
    void readEmail_ShouldReturnValidEmail() {
        when(sc.nextLine()).thenReturn("test@example.com");
        String result = consolePrinter.readEmail("Enter email:");
        assertEquals("test@example.com", result);
    }

    @Test
    void readEmail_ShouldHandleInvalidEmail() {
        when(sc.nextLine()).thenReturn("invalid-email", "test@example.com");
        String result = consolePrinter.readEmail("Enter email:");
        assertEquals("test@example.com", result);
    }

    @Test
    void readPassword_ShouldReturnValidPassword() {
        when(sc.nextLine()).thenReturn("password123");
        String result = consolePrinter.readPassword("Enter password:");
        assertEquals("password123", result);
    }

    @Test
    void readPassword_ShouldHandleShortPassword() {
        when(sc.nextLine()).thenReturn("123", "password123");
        String result = consolePrinter.readPassword("Enter password:");
        assertEquals("password123", result);
    }

    @Test
    void readDouble_ShouldReturnValidDouble() {
        when(sc.nextLine()).thenReturn("123.45");
        Double result = consolePrinter.readDouble("Enter a double:");
        assertEquals(123.45, result);
    }

    @Test
    void readDouble_ShouldHandleInvalidInput() {
        when(sc.nextLine()).thenReturn("invalid", "123.45");
        Double result = consolePrinter.readDouble("Enter a double:");
        assertEquals(123.45, result);
    }

    @Test
    void readInt_ShouldReturnValidInt() {
        when(sc.nextLine()).thenReturn("42");
        Integer result = consolePrinter.readInt("Enter an integer:");
        assertEquals(42, result);
    }


    @Test
    void readInt_ShouldHandleInvalidInput() {
        when(sc.nextLine()).thenReturn("invalid", "42");
        Integer result = consolePrinter.readInt("Enter an integer:");
        assertEquals(42, result);
    }

    @Test
    void readIntMenu_ShouldReturnValidInt() {
        when(sc.hasNextInt()).thenReturn(true);
        when(sc.nextInt()).thenReturn(42);
        int result = consolePrinter.readIntMenu("Enter an integer:");
        assertEquals(42, result);
        verify(sc).nextLine();
    }

    @Test
    void readIntMenu_ShouldHandleInvalidInput() {
        when(sc.hasNextInt()).thenReturn(false, true);
        when(sc.nextInt()).thenReturn(42);
        when(sc.next()).thenReturn("invalid");
        int result = consolePrinter.readIntMenu("Enter an integer:");
        assertEquals(42, result);
    }

    @Test
    void readBigDecimal_ShouldReturnValidBigDecimal() {
        when(sc.nextLine()).thenReturn("100.50");
        BigDecimal result = consolePrinter.readBigDecimal("Enter a decimal number:");
        assertEquals(new BigDecimal("100.50"), result);
    }

    @Test
    void readBigDecimal_ShouldHandleInvalidInput() {
        when(sc.nextLine()).thenReturn("invalid", "100.50");
        BigDecimal result = consolePrinter.readBigDecimal("Enter a decimal number:");
        assertEquals(new BigDecimal("100.50"), result);
    }

    @Test
    void readBoolean_ShouldReturnTrue() {
        when(sc.nextLine()).thenReturn("да");
        Boolean result = consolePrinter.readBoolean("Enter yes or no:");
        assertTrue(result);
    }

    @Test
    void readBoolean_ShouldReturnFalse() {
        when(sc.nextLine()).thenReturn("нет");
        Boolean result = consolePrinter.readBoolean("Enter yes or no:");
        assertFalse(result);
    }

    @Test
    void readBoolean_ShouldHandleInvalidInput() {
        when(sc.nextLine()).thenReturn("invalid", "да");
        Boolean result = consolePrinter.readBoolean("Enter yes or no:");
        assertTrue(result);
    }

    @Test
    void readDate_ShouldReturnValidDate() {
        when(sc.nextLine()).thenReturn("2023-10-01");
        LocalDate result = consolePrinter.readDate("Enter a date:");
        assertEquals(LocalDate.of(2023, 10, 1), result);
    }

    @Test
    void readDate_ShouldHandleInvalidInput() {
        when(sc.nextLine()).thenReturn("invalid", "2023-10-01");
        LocalDate result = consolePrinter.readDate("Enter a date:");
        assertEquals(LocalDate.of(2023, 10, 1), result);
    }

    @Test
    void isValidEmail_ShouldReturnTrueForValidEmail() throws Exception {
        Method isValidEmailMethod = ConsolePrinter.class.getDeclaredMethod("isValidEmail", String.class);
        isValidEmailMethod.setAccessible(true);

        boolean result = (boolean) isValidEmailMethod.invoke(consolePrinter, "test@example.com");
        assertTrue(result);
    }

    @Test
    void isValidEmail_ShouldReturnFalseForInvalidEmail() throws Exception {
        Method isValidEmailMethod = ConsolePrinter.class.getDeclaredMethod("isValidEmail", String.class);
        isValidEmailMethod.setAccessible(true);

        boolean result = (boolean) isValidEmailMethod.invoke(consolePrinter, "invalid-email");
        assertFalse(result);
    }

}