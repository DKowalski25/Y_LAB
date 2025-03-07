package dev.personal.financial.tracker.service.admin;

import dev.personal.financial.tracker.model.User;
import dev.personal.financial.tracker.repository.admin.AdminRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public List<User> getAllUsers() {
        return adminRepository.getAllUsers();
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