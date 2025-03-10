package dev.personal.financial.tracker.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransactionIn {
    private int userId;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private String description;
    private boolean isIncome;
}