package org.ahmedukamel.arrafni.dto.offer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOfferRequest(
        @NotBlank
        String title,

        @NotBlank
        String description,

        @NotNull
        Long businessId,

        @NotNull
        Integer offerPlanId
) {
}