package org.ahmedukamel.arrafni.dto.notification;

import java.time.LocalDateTime;
import java.util.Map;

public record UserNotificationResponse(
        Long id,

        String message,

        LocalDateTime timestamp,

        String type,

        int typeId,

        boolean read,

        Map<String, Object> metadata
) {
}