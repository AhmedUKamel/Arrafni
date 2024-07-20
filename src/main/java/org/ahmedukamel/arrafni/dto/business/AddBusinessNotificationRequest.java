package org.ahmedukamel.arrafni.dto.business;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddBusinessNotificationRequest(
        @NotNull
        @Min(value = 1)
        Long businessId,

        @NotNull
        @Min(value = 1)
        Integer planId
) {
}
