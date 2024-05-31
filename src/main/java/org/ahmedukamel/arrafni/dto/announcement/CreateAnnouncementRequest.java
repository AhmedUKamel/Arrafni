package org.ahmedukamel.arrafni.dto.announcement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAnnouncementRequest(
        @NotBlank
        String title,

        @NotBlank
        String description,

        @NotNull
        Long businessId,

        @NotNull
        Integer announcementPlanId,

        boolean premium
) {
}