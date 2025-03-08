package dev.personal.financial.tracker.dto.budget;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BudgetOut {
    private String id;
    private String userId;
    private double monthlyBudget;
}