package org.ahmedukamel.arrafni.mapper.notification;

import org.ahmedukamel.arrafni.dto.UserNotificationResponse;
import org.ahmedukamel.arrafni.model.UserNotification;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserNotificationResponseMapper implements Function<UserNotification, UserNotificationResponse> {
    @Override
    public UserNotificationResponse apply(UserNotification userNotification) {
        return new UserNotificationResponse(
                userNotification.getNotification().getId(),
                userNotification.getNotification().getMessage(),
                userNotification.getNotification().getTimestamp(),
                userNotification.getNotification().getType().name(),
                userNotification.getNotification().getType().getCode(),
                userNotification.isRead()
        );
    }
}