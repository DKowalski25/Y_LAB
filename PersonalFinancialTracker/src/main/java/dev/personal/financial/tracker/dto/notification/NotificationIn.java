package dev.personal.financial.tracker.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationIn {
    private String userId;
    private String message;
}