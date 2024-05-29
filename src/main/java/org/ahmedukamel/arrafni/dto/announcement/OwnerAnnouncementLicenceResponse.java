package org.ahmedukamel.arrafni.dto.announcement;

import java.time.LocalDateTime;

public record OwnerAnnouncementLicenceResponse(
        Long id,

        LocalDateTime creation,

        LocalDateTime expiration,

        boolean valid,

        boolean manual,

        Long announcementId,

        Integer announcementPlanId,

        Long paymentId
) {
}