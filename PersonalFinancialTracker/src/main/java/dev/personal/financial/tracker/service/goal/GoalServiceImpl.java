package dev.personal.financial.tracker.service.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalMapper;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.exception.goal.GoalNotFoundException;
import dev.personal.financial.tracker.model.Goal;
import dev.personal.financial.tracker.repository.goal.GoalRepository;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Реализация интерфейса {@link GoalService}.
 * Обрабатывает бизнес-логику, связанную с целями.
 */
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalRepository;

    @Override
    public void addGoal(GoalIn goalIn) {
        Goal goal = GoalMapper.toEntity(goalIn);
        goalRepository.save(goal);
    }

    @Override
    public GoalOut getGoalById(int id) {
        Goal goal = goalRepository.findById(id);
        return GoalMapper.toDto(goal);
    }

    @Override
    public GoalOut getGoalsByUserId(int userId) {
        Goal goal = goalRepository.findByUserId(userId);
        if (goal == null) {
            throw new GoalNotFoundException();
        }
        return GoalMapper.toDto(goal);
    }

    @Override
    public void updateGoal(GoalIn goalIn) {
        Goal goal = goalRepository.findById(goalIn.getId());
        GoalMapper.updateEntity(goal, goalIn);
        goalRepository.update(goal);
    }

    @Override
    public void deleteGoalByUserId(int userId) {
        Goal goal = goalRepository.findByUserId(userId);
        if (goal == null) {
            throw new GoalNotFoundException();
        }
        goalRepository.deleteByUserId(userId);
    }

    @Override
    public void updateSavedAmount(int goalId, BigDecimal amount) {
        goalRepository.updateSavedAmount(goalId, amount);
    }

    @Override
    public double getProgress(int goalId) {
        Goal goal = goalRepository.findById(goalId);
        return goal.getSavedAmount()
                .divide(goal.getGoalAmount(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}