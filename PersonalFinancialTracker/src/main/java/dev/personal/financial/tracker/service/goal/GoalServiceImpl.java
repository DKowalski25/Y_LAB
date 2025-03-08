package dev.personal.financial.tracker.service.goal;

import dev.personal.financial.tracker.dto.goal.GoalIn;
import dev.personal.financial.tracker.dto.goal.GoalMapper;
import dev.personal.financial.tracker.dto.goal.GoalOut;
import dev.personal.financial.tracker.model.Goal;
import dev.personal.financial.tracker.repository.goal.GoalRepository;

import lombok.RequiredArgsConstructor;

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
        if (goal != null) {
            return GoalMapper.toDto(goal);
        }
        return null;
    }

    @Override
    public GoalOut getGoalsByUserId(String userId) {
        Goal goal = goalRepository.findByUserId(userId);
        return goal != null ? GoalMapper.toDto(goal) : null;
    }

    @Override
    public void updateGoal(GoalIn goalIn) {
        Goal goal = goalRepository.findById(goalIn.getUserId());
        if (goal != null) {
            GoalMapper.updateEntity(goal, goalIn);
            goalRepository.update(goal);
        }
    }

    @Override
    public void deleteGoal(String userId) {
        goalRepository.delete(userId);
    }

    @Override
    public void updateSavedAmount(String userId, double amount) {
        goalRepository.updateSavedAmount(userId, amount);
    }

    @Override
    public double getProgress(String userId) {
        Goal goal = goalRepository.findById(userId);
        if (goal != null) {
            return (goal.getSavedAmount() / goal.getGoalAmount()) * 100;
        }
        return 0;
    }
}