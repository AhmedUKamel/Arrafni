package org.ahmedukamel.arrafni.dto.offer;

import jakarta.validation.constraints.NotNull;

public record RenewOfferRequest(
        @NotNull
        Long offerId,

        @NotNull
        Integer offerPlanId
) {
}