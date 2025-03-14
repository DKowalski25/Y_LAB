package dev.personal.financial.tracker.repository.notification;

import dev.personal.financial.tracker.exception.notification.NotificationAlreadyExistsException;
import dev.personal.financial.tracker.exception.notification.NotificationNotFoundException;
import dev.personal.financial.tracker.model.Notification;

import java.util.List;

/**
 * Интерфейс репозитория для работы с уведомлениями.
 * Предоставляет методы для сохранения, поиска и удаления уведомлений.
 */
public interface NotificationRepository {
    /**
     * Сохраняет уведомление.
     *
     * @param notification уведомление для сохранения
     * @throws NotificationAlreadyExistsException если уведомление с таким ID уже существует
     */
    void save(Notification notification);

    /**
     * Возвращает список уведомлений по ID пользователя.
     *
     * @param userId ID пользователя
     * @return список уведомлений
     */
    List<Notification> findByUserId(int userId);

    /**
     * Удаляет уведомление по ID.
     *
     * @param id ID уведомления
     * @throws NotificationNotFoundException если уведомление не найдено
     */
    void delete(int id);
}