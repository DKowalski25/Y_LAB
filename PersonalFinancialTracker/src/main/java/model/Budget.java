package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Budget {
    private String userId;
    private double monthlyBudget;
}