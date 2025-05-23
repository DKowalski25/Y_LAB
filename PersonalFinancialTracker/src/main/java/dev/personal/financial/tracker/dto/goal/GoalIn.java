package dev.personal.financial.tracker.dto.goal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class GoalIn {
    private int id;
    private int userId;
    private String goalName;
    private BigDecimal goalAmount;
}