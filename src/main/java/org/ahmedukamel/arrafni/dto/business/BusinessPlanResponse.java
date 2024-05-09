package org.ahmedukamel.arrafni.dto.business;

import java.math.BigDecimal;

public record BusinessPlanResponse(
        Integer id,
        String name,
        BigDecimal cost,
        Integer days,
        boolean active,
        int licences
) {
}