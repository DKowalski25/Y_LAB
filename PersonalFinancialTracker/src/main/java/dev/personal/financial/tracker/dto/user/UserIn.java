package dev.personal.financial.tracker.dto.user;

import dev.personal.financial.tracker.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserIn {
    private String id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
