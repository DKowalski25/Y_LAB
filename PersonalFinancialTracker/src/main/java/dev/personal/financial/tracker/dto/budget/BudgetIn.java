package dev.personal.financial.tracker.dto.budget;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BudgetIn {
    private String userId;
    private double monthlyBudget;
}