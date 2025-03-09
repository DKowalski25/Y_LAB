package dev.personal.financial.tracker.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationOut {
    private String id;
    private String userId;
    private String message;
    private LocalDateTime createdAt;
}