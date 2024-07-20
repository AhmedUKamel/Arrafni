package org.ahmedukamel.arrafni.dto.business;

import java.time.LocalDateTime;

public record AdminBusinessNotificationLicenceResponse(
        Long id,

        Long businessId,

        String name,

        String description,

        String address,

        String regionName,

        String logo,

        LocalDateTime requestDate,

        boolean valid,

        boolean manual
) {
}
