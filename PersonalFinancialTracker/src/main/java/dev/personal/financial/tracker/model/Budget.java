package dev.personal.financial.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Budget {
    private int id;
    private int userId;
    private BigDecimal monthlyBudget;
}