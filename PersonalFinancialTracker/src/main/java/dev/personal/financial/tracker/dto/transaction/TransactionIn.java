package dev.personal.financial.tracker.dto.transaction;

import dev.personal.financial.tracker.model.TransactionCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionIn {
    @NotNull(message = "User ID is required")
    private int userId;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private BigDecimal amount;

    @NotNull(message = "Category is required")
    private TransactionCategory category;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Is income is required")
    private boolean income;
}