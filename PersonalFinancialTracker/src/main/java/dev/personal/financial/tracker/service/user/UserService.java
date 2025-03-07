package dev.personal.financial.tracker.service.user;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserOut;
import dev.personal.financial.tracker.model.User;

public interface UserService {
    void registerUser(UserIn userIn);
    UserOut getUserById(String id);
    UserOut getUserByEmail(String email);
    void deleteUser(String id);
}