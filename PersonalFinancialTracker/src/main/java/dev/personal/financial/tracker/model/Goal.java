package dev.personal.financial.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Goal {
    private String userId;
    private String goalName;
    private double goalAmount;
    private double currentAmount;
}