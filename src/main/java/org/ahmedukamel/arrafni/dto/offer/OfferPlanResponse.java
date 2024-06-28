package org.ahmedukamel.arrafni.dto.offer;

import java.math.BigDecimal;

public record OfferPlanResponse(
        Integer id,

        String name,

        BigDecimal cost,

        Integer days,

        boolean active,

        int licences
) {
}