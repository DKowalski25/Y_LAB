package dev.personal.financial.tracker.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransactionIn {
    private String userId;
    private double amount;
    private String category;
    private LocalDate date;
    private String description;
    private boolean isIncome;
}