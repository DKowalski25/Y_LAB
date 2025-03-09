package dev.personal.financial.tracker.service.user;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserOut;

public interface UserService {
    void registerUser(UserIn userIn);
    UserOut getUserById(String id);
    UserOut getUserByEmail(String email);
    void updateUser(String email,UserIn userIn);
    void deleteUserEmail(String email);
}