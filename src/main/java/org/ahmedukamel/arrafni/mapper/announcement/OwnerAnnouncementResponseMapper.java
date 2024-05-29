package org.ahmedukamel.arrafni.mapper.announcement;

import org.ahmedukamel.arrafni.dto.announcement.OwnerAnnouncementResponse;
import org.ahmedukamel.arrafni.model.Announcement;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class OwnerAnnouncementResponseMapper implements Function<Announcement, OwnerAnnouncementResponse> {
    @Override
    public OwnerAnnouncementResponse apply(Announcement announcement) {
        return new OwnerAnnouncementResponse(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getDescription(),
                // TODO: Map poster link
                announcement.getPoster(),
                announcement.getCreation(),
                announcement.getExpiration(),
                announcement.isActive(),
                announcement.isBlocked(),
                announcement.isPremium(),
                announcement.getBusiness().getId()
        );
    }
}