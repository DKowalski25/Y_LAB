package dev.personal.financial.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Budget {
    private String id;
    private String userId;
    private double monthlyBudget;
}