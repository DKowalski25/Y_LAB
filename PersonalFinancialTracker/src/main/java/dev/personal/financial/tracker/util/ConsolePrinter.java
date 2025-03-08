package dev.personal.financial.tracker.util;

import lombok.RequiredArgsConstructor;

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
            System.out.println(prompt);
            String input = sc.nextLine();
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
            if (password.length() >= 6) {
                return password;
            } else {
                printError("Пароль должен содержать не менее 6 символов.");
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            printPrompt(prompt);
            if (sc.hasNextDouble()) {
                double value = sc.nextDouble();
                sc.nextLine();
                return value;
            } else {
                printError("Неверный формат числа. Пожалуйста, введите корректное число.");
                sc.next();
            }
        }
    }

    public int readInt(String prompt) {
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

    public boolean readBoolean(String prompt) {
        while (true) {
            printPrompt(prompt + "(true/false):");
            if (sc.hasNextBoolean()) {
                return sc.nextBoolean();
            } else {
                printError("Неверный формат логического значения. Пожалуйста, введите true или false.");
                sc.next();
            }
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
}