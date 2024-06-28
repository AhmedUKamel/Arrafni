package org.ahmedukamel.arrafni.dto.offer;

import java.time.LocalDateTime;

public record OwnerOfferResponse(
        Long id,

        String title,

        String description,

        String poster,

        LocalDateTime creation,

        LocalDateTime expiration,

        boolean activated,

        boolean blocked,

        Long businessId
) {
}