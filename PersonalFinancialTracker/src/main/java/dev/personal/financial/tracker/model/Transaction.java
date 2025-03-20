package dev.personal.financial.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Transaction {
    private int id;
    private int userId;
    private BigDecimal amount;
    private TransactionCategory category;
    private LocalDate date;
    private String description;
    private boolean isIncome;
}