package org.ahmedukamel.arrafni.dto.announcement;

public record RenewAnnouncementRequest(
        Long announcementId,

        Integer announcementPlanId,

        boolean premium
) {
}