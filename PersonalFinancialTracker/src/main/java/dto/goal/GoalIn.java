package dto.goal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoalIn {
    private String userId;
    private String goalName;
    private double goalAmount;
}