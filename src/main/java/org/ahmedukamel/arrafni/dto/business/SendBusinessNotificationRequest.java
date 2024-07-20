package org.ahmedukamel.arrafni.dto.business;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendBusinessNotificationRequest(
        @NotNull
        @Min(value = 1)
        Long businessId,

        @NotBlank
        String title,

        @NotBlank
        String body
) {
}
