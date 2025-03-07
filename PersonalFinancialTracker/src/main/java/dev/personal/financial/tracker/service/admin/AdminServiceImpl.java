package dev.personal.financial.tracker.service.admin;

import dev.personal.financial.tracker.dto.user.UserMapper;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.repository.admin.AdminRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public List<UserOut> getAllUsers() {
        List<User> users = adminRepository.getAllUsers();
        return users.stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public void blockUser(String userId) {
        adminRepository.blockUser(userId);
    }

    @Override
    public void deleteUser(String userId) {
        adminRepository.deleteUser(userId);
    }
}