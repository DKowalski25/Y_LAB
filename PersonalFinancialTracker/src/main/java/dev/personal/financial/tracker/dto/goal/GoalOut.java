package dev.personal.financial.tracker.dto.goal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoalOut {
    private String id;
    private String userId;
    private String goalName;
    private double goalAmount;
    private double currentAmount;
}