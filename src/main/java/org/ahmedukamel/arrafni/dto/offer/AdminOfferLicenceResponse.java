package org.ahmedukamel.arrafni.dto.offer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AdminOfferLicenceResponse(
        Long offerId,

        boolean offerActive,

        boolean offerBlocked,

        boolean offerDeleted,

        LocalDateTime offerCreation,

        LocalDateTime offerExpiration,

        Long licenceId,

        LocalDateTime licenceCreation,

        Integer planId,

        BigDecimal planOriginalCost,

        Integer planActiveDays
) {
}