package org.ahmedukamel.arrafni.dto.announcement;

import jakarta.validation.constraints.NotNull;

public record RenewAnnouncementRequest(
        @NotNull
        Long announcementId,

        @NotNull
        Integer announcementPlanId,

        boolean premium
) {
}