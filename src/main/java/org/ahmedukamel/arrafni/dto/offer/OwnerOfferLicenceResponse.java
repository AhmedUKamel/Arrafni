package org.ahmedukamel.arrafni.dto.offer;

import java.time.LocalDateTime;

public record OwnerOfferLicenceResponse(
        Long id,

        LocalDateTime creation,

        LocalDateTime expiration,

        boolean valid,

        boolean manual,

        Long offerId,

        Integer offerPlanId,

        Long paymentId
) {
}