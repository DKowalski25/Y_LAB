package dev.personal.financial.tracker.service.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalMapper;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.model.Goal;
import dev.personal.financial.tracker.repository.goal.GoalRepository;

import lombok.RequiredArgsConstructor;

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
    public GoalOut getGoalById(String id) {
        Goal goal = goalRepository.findById(id);
        return GoalMapper.toDto(goal);
    }

    @Override
    public GoalOut getGoalsByUserId(String userId) {
        Goal goal = goalRepository.findByUserId(userId);
        return GoalMapper.toDto(goal);
    }

    @Override
    public void updateGoal(GoalIn goalIn) {
        Goal goal = goalRepository.findById(goalIn.getId());
        GoalMapper.updateEntity(goal, goalIn);
        goalRepository.update(goal);
    }

    @Override
    public void deleteGoalByUserId(String userId) {
        goalRepository.deleteByUserId(userId);
    }

    @Override
    public void updateSavedAmount(String goalId, double amount) {
        goalRepository.updateSavedAmount(goalId, amount);
    }

    @Override
    public double getProgress(String goalId) {
        Goal goal = goalRepository.findById(goalId);
        return (goal.getSavedAmount() / goal.getGoalAmount()) * 100;
    }
}