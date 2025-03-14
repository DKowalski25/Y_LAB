package dev.personal.financial.tracker.dto.budget;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BudgetIn {
    private int id;
    private int userId;
    private BigDecimal monthlyBudget;
}