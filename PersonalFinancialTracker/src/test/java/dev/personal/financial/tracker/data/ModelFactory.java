package dev.personal.financial.tracker.data;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ModelFactory {

    public static User createAdminUser() {
        return new User(
                0,
                "Admin",
                "admin@example",
                "password",
                UserRole.ADMIN,
                true
        );
    }
    public static User createUser() {
        return new User(
                0, // ID будет сгенерирован базой данных
                "John Doe",
                "john@example.com",
                "password",
                UserRole.USER,
                true
        );
    }

    public static User createUser(String name, String email, UserRole role) {
        return new User(
                0,
                name,
                email,
                "password",
                role,
                true
        );
    }

    public static User createUser(int id, String name, String email, String password, UserRole role, boolean isBlocked) {
        return new User(
                id,
                name,
                email,
                password,
                role,
                isBlocked
        );
    }

    public static UserIn createUserIn() {
        return new UserIn(
                0,
                "John Doe",
                "john@example.com",
                "password",
                UserRole.USER
        );
    }

    public static UserIn createUserIn(int id, String name, String email, String password, UserRole role) {
        return new UserIn(
                id,
                name,
                email,
                password,
                role
        );
    }

    public static UserOut createUserOut(int id, String name, String email, UserRole role, Boolean isBlocked) {
        return new UserOut(
                id,
                name,
                email,
                role,
                isBlocked
        );
    }

    public static Transaction createTransaction() {
        return new Transaction(
                0,
                1,
                new BigDecimal("100.00"),
                "Category",
                LocalDate.of(2023, 6, 1),
                "Description",
                true
        );
    }

    public static Notification createNotification() {
        return new Notification(
                0,
                1,
                "Test message",
                LocalDateTime.now()
        );
    }

    public static Goal createGoal() {
        return new Goal(
                0,
                1,
                "Buy a car",
                new BigDecimal("10000.00"),
                new BigDecimal("0.00"),
                new BigDecimal("0.00")
        );
    }

    public static Budget createBudget() {
        return new Budget(
                0,
                1,
                new BigDecimal("1000.00")
        );
    }
}