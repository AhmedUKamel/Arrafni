package org.ahmedukamel.arrafni.dto.announcement;

import java.time.LocalDateTime;

public record OwnerAnnouncementResponse(
        Long id,

        String title,

        String description,

        String poster,

        LocalDateTime creation,

        LocalDateTime expiration,

        boolean activated,

        boolean blocked,

        boolean premium,

        boolean deleted,

        Long businessId
) {
}