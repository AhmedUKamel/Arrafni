package org.ahmedukamel.arrafni.dto.announcement;

public record CreateAnnouncementRequest(
        String title,

        String description,

        Long businessId,

        Integer announcementPlanId,

        boolean premium
) {
}