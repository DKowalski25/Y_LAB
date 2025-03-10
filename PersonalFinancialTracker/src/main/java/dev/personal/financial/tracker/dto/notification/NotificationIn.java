package dev.personal.financial.tracker.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationIn {
    private int userId;
    private String message;
}