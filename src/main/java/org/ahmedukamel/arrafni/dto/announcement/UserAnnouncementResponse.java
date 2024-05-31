package org.ahmedukamel.arrafni.dto.announcement;

import java.time.LocalDateTime;

public record UserAnnouncementResponse(
        Long id,

        Long businessId,

        String title,

        String description,

        String poster,

        LocalDateTime creation,

        LocalDateTime expiration
) {
}