package org.ahmedukamel.arrafni.dto.plan;

import java.math.BigDecimal;

public record PlanResponse(
        Integer id,
        String name,
        BigDecimal cost,
        Integer days,
        boolean active,
        int licences
) {
}