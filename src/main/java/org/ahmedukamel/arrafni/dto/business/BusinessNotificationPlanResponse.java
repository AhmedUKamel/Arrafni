package org.ahmedukamel.arrafni.dto.business;

import java.math.BigDecimal;

public record BusinessNotificationPlanResponse(
        Integer id,

        String name,

        BigDecimal cost,

        Integer count,

        boolean active
) {
}
