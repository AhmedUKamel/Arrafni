package org.ahmedukamel.arrafni.dto.announcement;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateAnnouncementPlanRequest(
        @NotBlank
        String name,

        @NotNull
        @Min(value = 0)
        BigDecimal cost,

        @NotNull
        @Min(value = 0)
        BigDecimal premiumCost,

        @NotNull
        @Min(value = 1)
        Integer days
) {
}