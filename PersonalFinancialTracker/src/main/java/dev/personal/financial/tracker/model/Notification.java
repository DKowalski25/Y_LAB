package dev.personal.financial.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Notification {
    private String id;
    private String userId;
    private String message;
    private LocalDateTime createdAt;

    public Notification(String userId, String message) {
        this.userId = userId;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

}