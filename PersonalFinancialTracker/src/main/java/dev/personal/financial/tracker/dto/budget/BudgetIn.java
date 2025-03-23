package dev.personal.financial.tracker.dto.budget;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BudgetIn {
    private int id;

    @NotNull(message = "User ID is required")
    private int userId;

    @NotNull(message = "Monthly budget is required")
    @Min(value = 0, message = "Monthly budget must be greater than or equal to 0")
    private BigDecimal monthlyBudget;
}