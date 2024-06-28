package org.ahmedukamel.arrafni.dto.offer;

import java.time.LocalDateTime;

public record UserOfferResponse(
        Long id,

        Long businessId,

        String title,

        String description,

        String poster,

        LocalDateTime creation,

        LocalDateTime expiration
) {
}