package org.ahmedukamel.arrafni.dto.announcement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AdminAnnouncementLicenceResponse(
        Long announcementId,

        boolean announcementActive,

        boolean announcementBlocked,

        boolean announcementDeleted,

        boolean announcementPremium,

        LocalDateTime announcementCreation,

        LocalDateTime announcementExpiration,

        Long licenceId,

        LocalDateTime licenceCreation,

        boolean licencePremium,

        Integer planId,

        BigDecimal planOriginalCost,

        BigDecimal planPremiumCost,

        Integer planActiveDays
) {
}