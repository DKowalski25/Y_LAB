package dev.personal.financial.tracker.service.notification;

import dev.personal.financial.tracker.dto.notification.NotificationIn;
import dev.personal.financial.tracker.dto.notification.NotificationMapper;
import dev.personal.financial.tracker.dto.notification.NotificationOut;
import dev.personal.financial.tracker.exception.notification.NotificationAlreadyExistsException;
import dev.personal.financial.tracker.exception.notification.NotificationNotFoundException;
import dev.personal.financial.tracker.model.Notification;
import dev.personal.financial.tracker.repository.notification.NotificationRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса {@link NotificationService}.
 * Обрабатывает бизнес-логику, связанную с уведомлениями.
 */
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public void sendNotification(NotificationIn notificationIn) {
        Notification notification = NotificationMapper.toEntity(notificationIn);
        notificationRepository.save(notification);
        // Здесь добавится логика отправки уведомления, когда приложение перейдет в веб
    }

    @Override
    public List<NotificationOut> getNotificationsByUserId(String userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(NotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNotification(String id) {
        notificationRepository.delete(id);
    }

    @Override
    public void sendEmailNotification(String email, String subject, String message) {
        // Замоканная реализация отправки email
        System.out.println("Sending email to: " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
    }
}