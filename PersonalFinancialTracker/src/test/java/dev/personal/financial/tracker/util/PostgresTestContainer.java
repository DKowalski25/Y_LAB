package dev.personal.financial.tracker.util;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Testcontainers
public class PostgresTestContainer {

    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword");

    public static void start() {
        postgres.start();
    }

    public static void stop() {
        postgres.stop();
    }

    public static void setUpDatabase() throws SQLException {
        String jdbcUrl = postgres.getJdbcUrl();
        String username = postgres.getUsername();
        String password = postgres.getPassword();

        connection = DriverManager.getConnection(jdbcUrl, username, password);
    }

    public static void tearDownDatabase() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void cleanUpDatabase() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.execute("DELETE FROM users");
        }
    }

    public static void executeSql(String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    public static void createUserTable() throws SQLException {
        executeSql("""
                    CREATE TABLE users (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        role VARCHAR(50) NOT NULL,
                        is_blocked BOOLEAN NOT NULL
                    )
                """);
    }

    public static void createTransactionTable() throws SQLException {
        executeSql("""
                    CREATE TABLE transactions (
                        id SERIAL PRIMARY KEY,
                        user_id INT NOT NULL,
                        amount DECIMAL(19, 2) NOT NULL,
                        category VARCHAR(255) NOT NULL,
                        date DATE NOT NULL,
                        description VARCHAR(255) NOT NULL,
                        is_income BOOLEAN NOT NULL
                    )
                """);
    }

    public static void createNotificationTable() throws SQLException {
        executeSql("""
                    CREATE TABLE notifications (
                        id SERIAL PRIMARY KEY,
                        user_id INT NOT NULL,
                        message TEXT NOT NULL,
                        created_at TIMESTAMP NOT NULL
                    )
                """);
    }

    public static void createGoalTable() throws SQLException {
        executeSql("""
                    CREATE TABLE goals (
                        id SERIAL PRIMARY KEY,
                        user_id INT NOT NULL,
                        goal_name VARCHAR(255) NOT NULL,
                        goal_amount DECIMAL(19, 2) NOT NULL,
                        current_amount DECIMAL(19, 2) NOT NULL,
                        saved_amount DECIMAL(19, 2) NOT NULL
                    )
                """);
    }

    public static void createBudgetTable() throws SQLException {
        executeSql("""
                    CREATE TABLE budgets (
                        id SERIAL PRIMARY KEY,
                        user_id INT NOT NULL,
                        monthly_budget DECIMAL(19, 2) NOT NULL
                    )
                """);
    }
}