package dev.personal.financial.tracker.service.notification;

import dev.personal.financial.tracker.dto.notification.NotificationIn;
import dev.personal.financial.tracker.dto.notification.NotificationOut;
import dev.personal.financial.tracker.exception.notification.NotificationAlreadyExistsException;
import dev.personal.financial.tracker.exception.notification.NotificationNotFoundException;

import java.util.List;

/**
 * Интерфейс сервиса для работы с уведомлениями.
 * Предоставляет методы для отправки, поиска и удаления уведомлений.
 */
public interface NotificationService {
    /**
     * Отправляет уведомление.
     *
     * @param notificationIn данные уведомления
     * @throws NotificationAlreadyExistsException если уведомление с таким ID уже существует
     */
    void sendNotification(NotificationIn notificationIn);

    /**
     * Возвращает список уведомлений по ID пользователя.
     *
     * @param userId ID пользователя
     * @return список уведомлений
     */
    List<NotificationOut> getNotificationsByUserId(String userId);

    /**
     * Удаляет уведомление по ID.
     *
     * @param id ID уведомления
     * @throws NotificationNotFoundException если уведомление не найдено
     */
    void deleteNotification(String id);

    /**
     * Отправляет email-уведомление. // пока что мок
     *
     * @param email адрес электронной почты
     * @param subject тема письма
     * @param message текст письма
     */
    void sendEmailNotification(String email, String subject, String message);
}