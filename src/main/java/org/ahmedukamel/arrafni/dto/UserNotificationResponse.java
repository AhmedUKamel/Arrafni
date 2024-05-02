package org.ahmedukamel.arrafni.dto;

import java.time.LocalDateTime;

public record UserNotificationResponse(
        Long id,
        String message,
        LocalDateTime timestamp,
        String type,
        int typeId,
        boolean read
) {
}