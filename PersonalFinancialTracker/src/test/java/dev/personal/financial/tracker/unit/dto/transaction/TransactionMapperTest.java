package dev.personal.financial.tracker.unit.dto.transaction;

import dev.personal.financial.tracker.dto.transaction.TransactionIn;
import dev.personal.financial.tracker.dto.transaction.TransactionMapper;
import dev.personal.financial.tracker.dto.transaction.TransactionOut;
import dev.personal.financial.tracker.model.Transaction;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionMapperTest {

    private static final int ID = UUID.randomUUID().hashCode();
    private static final int USER_ID = UUID.randomUUID().hashCode();


    @Test
    void toEntity_ShouldMapTransactionInToTransaction() {
        TransactionIn transactionIn = new TransactionIn(
                ID,
                BigDecimal.valueOf(100.0),
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );

        Transaction transaction = TransactionMapper.toEntity(transactionIn);

        assertThat(transaction.getId()).isNotZero();
        assertThat(transaction.getUserId()).isEqualTo(ID);
        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.valueOf(100.0));
        assertThat(transaction.getCategory()).isEqualTo("Food");
        assertThat(transaction.getDate()).isEqualTo(LocalDate.now());
        assertThat(transaction.getDescription()).isEqualTo("Lunch");
        assertThat(transaction.isIncome()).isTrue();
    }

    @Test
    void toDto_ShouldMapTransactionToTransactionOut() {
        Transaction transaction = new Transaction(
                ID,
                USER_ID,
                BigDecimal.valueOf(100.0),
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );

        TransactionOut transactionOut = TransactionMapper.toDto(transaction);

        assertThat(transactionOut.getId()).isEqualTo(transaction.getId());
        assertThat(transactionOut.getUserId()).isEqualTo(USER_ID);
        assertThat(transactionOut.getAmount()).isEqualTo(BigDecimal.valueOf(100.0));
        assertThat(transactionOut.getCategory()).isEqualTo("Food");
        assertThat(transactionOut.getDate()).isEqualTo(LocalDate.now());
        assertThat(transactionOut.getDescription()).isEqualTo("Lunch");
        assertThat(transactionOut.isIncome()).isTrue();
    }

    @Test
    void updateTransaction_ShouldUpdateTransactionFields() {
        Transaction transaction = new Transaction(
                ID,
                USER_ID,
                BigDecimal.valueOf(100.0),
                "Food",
                LocalDate.now(),
                "Lunch",
                true
        );
        TransactionIn transactionIn = new TransactionIn(
                USER_ID,
                BigDecimal.valueOf(200.0),
                "Transport",
                LocalDate.now().plusDays(1),
                "Taxi",
                false
        );

        TransactionMapper.updateTransaction(transaction, transactionIn);

        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.valueOf(200.0));
        assertThat(transaction.getCategory()).isEqualTo("Transport");
        assertThat(transaction.getDate()).isEqualTo(LocalDate.now().plusDays(1));
        assertThat(transaction.getDescription()).isEqualTo("Taxi");
        assertThat(transaction.isIncome()).isFalse();
    }
}