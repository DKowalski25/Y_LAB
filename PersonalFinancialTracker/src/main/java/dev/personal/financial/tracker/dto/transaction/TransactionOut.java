package dev.personal.financial.tracker.dto.transaction;

import dev.personal.financial.tracker.model.TransactionCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransactionOut {
    private int id;
    private int userId;
    private BigDecimal amount;
    private TransactionCategory category;
    private LocalDate date;
    private String description;
    private boolean isIncome;
}