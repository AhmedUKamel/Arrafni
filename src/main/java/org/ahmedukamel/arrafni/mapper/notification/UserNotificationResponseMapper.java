package org.ahmedukamel.arrafni.mapper.notification;

import org.ahmedukamel.arrafni.dto.notification.UserNotificationResponse;
import org.ahmedukamel.arrafni.model.UserNotification;
import org.ahmedukamel.arrafni.model.enumration.NotificationType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class UserNotificationResponseMapper implements Function<UserNotification, UserNotificationResponse> {
    @Override
    public UserNotificationResponse apply(UserNotification userNotification) {
        Map<String, Object> metadata = new HashMap<>();
        if (userNotification.getNotification().getType().equals(NotificationType.BUSINESS)) {
            metadata.put("body", userNotification.getNotification().getBusinessNotification().getBody());
            metadata.put("businessId", userNotification.getNotification().getBusinessNotification().getBusiness().getId());
        }

        return new UserNotificationResponse(
                userNotification.getNotification().getId(),
                userNotification.getNotification().getMessage(),
                userNotification.getNotification().getTimestamp(),
                userNotification.getNotification().getType().name(),
                userNotification.getNotification().getType().getCode(),
                userNotification.isRead(),
                metadata
        );
    }
}