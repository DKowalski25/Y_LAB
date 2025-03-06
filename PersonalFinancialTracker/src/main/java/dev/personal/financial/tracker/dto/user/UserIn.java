package dev.personal.financial.tracker.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserIn {
    private String name;
    private String email;
    private String password;
    private String role;
}
