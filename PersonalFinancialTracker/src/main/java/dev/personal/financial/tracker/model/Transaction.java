package dev.personal.financial.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Transaction {
    private String id;
    private String userId;
    private double amount;
    private String category;
    private LocalDate date;
    private String description;
    private boolean isIncome;
}