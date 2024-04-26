package org.ahmedukamel.sohagy.dto.business;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateBusinessPlanRequest(
        @NotBlank
        String name,

        @NotNull
        @Min(value = 0)
        BigDecimal cost,

        @NotNull
        @Min(value = 1)
        Integer days
) {
}