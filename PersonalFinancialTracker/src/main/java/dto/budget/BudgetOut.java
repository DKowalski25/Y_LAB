package dto.budget;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BudgetOut {
    private String userId;
    private double monthlyBudget;
}