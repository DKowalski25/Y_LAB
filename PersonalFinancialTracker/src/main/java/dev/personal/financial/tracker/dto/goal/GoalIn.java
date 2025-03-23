package dev.personal.financial.tracker.dto.goal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class GoalIn {
    private int id;

    @NotNull(message = "User ID is required")
    private int userId;

    @NotBlank(message = "Goal name is required")
    @Size(max = 100, message = "Goal name cannot exceed 100 characters")
    private String goalName;

    @NotNull(message = "Goal amount is required")
    @Min(value = 0, message = "Goal amount must be greater than or equal to 0")
    private BigDecimal goalAmount;
}