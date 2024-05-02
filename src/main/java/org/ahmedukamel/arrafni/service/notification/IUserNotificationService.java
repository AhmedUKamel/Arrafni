package org.ahmedukamel.arrafni.service.notification;

import org.ahmedukamel.arrafni.model.enumration.NotificationType;

public interface IUserNotificationService {
    void readNotification(Long id);

    void deleteNotification(Long id);

    Object getNotification(Long id);

    Object getNotifications(long pageSize, long pageNumber, NotificationType type);
}