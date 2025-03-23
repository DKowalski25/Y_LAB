package dev.personal.financial.tracker.util;

import dev.personal.financial.tracker.model.TransactionCategory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class ConsolePrinter {
    private final Scanner sc;

    public void printPrompt(String msg) {
        System.out.println(msg);
    }

    public void printSuccess(String msg) {
        System.out.println("✅ " + msg);
    }

    public void printError(String msg) {
        System.out.println("❌ " + msg);
    }

    public void printInfo(String msg) {
        System.out.println("ℹ️ " + msg);
    }

    public void printWithDivider(String msg) {
        System.out.println("====================");
        System.out.println(msg);
        System.out.println("====================");
    }

    public String readNonEmptyString(String prompt) {
        while (true) {
            System.out.println(prompt + " (или введите 'q' для выхода):");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("q")) {
                return null;
            }
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            } else {
                printError("Поле не может быть пустым. Пожалуйста, введите значение.");
            }
        }
    }

    public String readEmail(String prompt) {
        while (true) {
            String email = readNonEmptyString(prompt);
            if (email == null) {
                return null;
            }
            if (isValidEmail(email)) {
                return email;
            } else {
                printError("Неверный формат email. Пожалуйста, введите корректный email.\nПример: Example@example.com.");
            }
        }
    }

    public String readPassword(String prompt) {
        while (true) {
            String password = readNonEmptyString(prompt);
            if (password == null) {
                return null;
            }
            if (password.length() >= 6) {
                return password;
            } else {
                printError("Пароль должен содержать не менее 6 символов.");
            }
        }
    }

    public Double readDouble(String prompt) {
        while (true) {
            printPrompt(prompt + " (или введите 'q' для выхода):");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("q")) {
                return null;
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                printError("Неверный формат числа. Пожалуйста, введите корректное число.");
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            printPrompt(prompt + " (или введите 'q' для выхода):");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("q")) {
                return null;
            }
            try {
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                printError("Неверный формат числа. Пожалуйста, введите корректное число.");
            }
        }
    }

    public int readIntMenu(String prompt) {
        while (true) {
            printPrompt(prompt);
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            } else {
                printError("Неверный формат числа. Пожалуйста, введите корректное число.");
                sc.next();
            }
        }
    }

    public Integer readInt(String prompt) {
        while (true) {
            printPrompt(prompt + " (или введите 'q' для выхода):");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("q")) {
                return null;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                printError("Неверный формат числа. Пожалуйста, введите целое число.");
            }
        }
    }

    public Boolean readBoolean(String prompt) {
        while (true) {
            printPrompt(prompt + " (да/нет) (или введите 'q' для выхода):");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("q")) {
                return null;
            }
            if (input.equalsIgnoreCase("да")) {
                return true;
            } else if (input.equalsIgnoreCase("нет")) {
                return false;
            } else {
                printError("Неверный ввод. Пожалуйста, введите 'да' или 'нет'.");
            }
        }
    }

    public LocalDate readDate(String prompt) {
        while (true) {
            printPrompt(prompt + " (гггг-мм-дд) (или введите 'q' для выхода):");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("q")) {
                return null;
            }
            try {
                return LocalDate.parse(input);
            } catch (Exception e) {
                printError("Неверный формат даты. Пожалуйста, введите дату в формате гггг-мм-дд.");
            }
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    public TransactionCategory readTransactionCategory(String prompt) {
        while (true) {
            printPrompt(prompt + " (или введите 'q' для выхода):");
            printPrompt("Доступные категории: " + Arrays.toString(TransactionCategory.values()));
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("q")) {
                return null;
            }
            try {
                return TransactionCategory.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                printError("Неверная категория. Пожалуйста, выберите категорию из списка.");
            }
        }
    }
}