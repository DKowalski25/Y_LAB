package dev.personal.financial.tracker.dto.transaction;

import dev.personal.financial.tracker.model.Transaction;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionMapperTest {

    @Test
    void toEntity_ShouldMapTransactionInToTransaction() {
        TransactionIn transactionIn = new TransactionIn(
                "user1",
                100.0,
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );

        Transaction transaction = TransactionMapper.toEntity(transactionIn);

        assertThat(transaction.getId()).isNotNull();
        assertThat(transaction.getUserId()).isEqualTo("user1");
        assertThat(transaction.getAmount()).isEqualTo(100.0);
        assertThat(transaction.getCategory()).isEqualTo("Food");
        assertThat(transaction.getDate()).isEqualTo(LocalDate.now());
        assertThat(transaction.getDescription()).isEqualTo("Lunch");
        assertThat(transaction.isIncome()).isTrue();
    }

    @Test
    void toDto_ShouldMapTransactionToTransactionOut() {
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                "user1",
                100.0,
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );

        TransactionOut transactionOut = TransactionMapper.toDto(transaction);

        assertThat(transactionOut.getId()).isEqualTo(transaction.getId());
        assertThat(transactionOut.getUserId()).isEqualTo("user1");
        assertThat(transactionOut.getAmount()).isEqualTo(100.0);
        assertThat(transactionOut.getCategory()).isEqualTo("Food");
        assertThat(transactionOut.getDate()).isEqualTo(LocalDate.now());
        assertThat(transactionOut.getDescription()).isEqualTo("Lunch");
        assertThat(transactionOut.isIncome()).isTrue();
    }

    @Test
    void updateTransaction_ShouldUpdateTransactionFields() {
        Transaction transaction = new Transaction(
                "1",
                "user1",
                100.0,
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );
        TransactionIn transactionIn = new TransactionIn(
                "user1",
                200.0,
                "Transport",
                LocalDate.now().plusDays(1),
                "Taxi",
                false
        );

        TransactionMapper.updateTransaction(transaction, transactionIn);

        assertThat(transaction.getAmount()).isEqualTo(200.0);
        assertThat(transaction.getCategory()).isEqualTo("Transport");
        assertThat(transaction.getDate()).isEqualTo(LocalDate.now().plusDays(1));
        assertThat(transaction.getDescription()).isEqualTo("Taxi");
        assertThat(transaction.isIncome()).isFalse();
    }
}