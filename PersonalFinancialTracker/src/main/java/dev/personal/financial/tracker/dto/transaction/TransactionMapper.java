package dev.personal.financial.tracker.dto.transaction;

import dev.personal.financial.tracker.model.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TransactionMapper {

    public static Transaction toEntity(TransactionIn transactionIn) {
        return new Transaction(
                UUID.randomUUID().hashCode(),
                transactionIn.getUserId(),
                transactionIn.getAmount(),
                transactionIn.getCategory(),
                transactionIn.getDate(),
                transactionIn.getDescription(),
                transactionIn.isIncome()
        );
    }

    public static TransactionOut toDto(Transaction transaction) {
        return new TransactionOut(
                transaction.getId(),
                transaction.getUserId(),
                transaction.getAmount(),
                transaction.getCategory(),
                transaction.getDate(),
                transaction.getDescription(),
                transaction.isIncome()
        );
    }

    public static void updateTransaction(Transaction transaction, TransactionIn transactionIn) {
        transaction.setAmount(transactionIn.getAmount());
        transaction.setCategory(transactionIn.getCategory());
        transaction.setDate(transactionIn.getDate());
        transaction.setDescription(transactionIn.getDescription());
        transaction.setIncome(transactionIn.isIncome());
    }

    public static Transaction mapRowToTransaction(ResultSet resultSet) throws SQLException {
        return new Transaction(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getBigDecimal("amount"),
                resultSet.getString("category"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getString("description"),
                resultSet.getBoolean("is_income")
        );
    }
}