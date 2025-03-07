package dev.personal.financial.tracker.service.goal;

import dev.personal.financial.tracker.model.Goal;
import dev.personal.financial.tracker.repository.goal.GoalRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalRepository;

    @Override
    public void addGoal(Goal goal) {
        goalRepository.save(goal);
    }

    @Override
    public List<Goal> getGoalsByUserId(String userId) {
        return goalRepository.findByUserId(userId);
    }

    @Override
    public void updateGoal(Goal goal) {
        goalRepository.update(goal);
    }

    @Override
    public void deleteGoal(String userId) {
        goalRepository.delete(userId);
    }
}