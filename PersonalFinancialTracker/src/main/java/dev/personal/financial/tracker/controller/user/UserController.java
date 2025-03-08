package dev.personal.financial.tracker.controller.user;

import dev.personal.financial.tracker.dto.user.UserIn;
import dev.personal.financial.tracker.dto.user.UserOut;


public interface UserController {
    void registerUser(UserIn userIn);
    UserOut getUserById(String id);
    UserOut getUserByEmail(String email);
    void updateUser(String email, UserIn userIn);
    void deleteUserByEmail(String email);
}