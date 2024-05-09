package org.ahmedukamel.arrafni.dto.announcement;

import java.math.BigDecimal;

public record AnnouncementPlanResponse(
        Integer id,
        String name,
        BigDecimal cost,
        BigDecimal premiumCost,
        Integer days,
        boolean active,
        int licences
) {
}