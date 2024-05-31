package org.ahmedukamel.arrafni.mapper.announcement;

import org.ahmedukamel.arrafni.dto.announcement.AdminAnnouncementLicenceResponse;
import org.ahmedukamel.arrafni.model.AnnouncementLicence;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AdminAnnouncementLicenceResponseMapper
        implements Function<AnnouncementLicence, AdminAnnouncementLicenceResponse> {

    @Override
    public AdminAnnouncementLicenceResponse apply(AnnouncementLicence licence) {
        return new AdminAnnouncementLicenceResponse(
                licence.getAnnouncement().getId(),
                licence.getAnnouncement().isActive(),
                licence.getAnnouncement().isBlocked(),
                licence.getAnnouncement().isDeleted(),
                licence.getAnnouncement().isPremium(),
                licence.getAnnouncement().getCreation(),
                licence.getAnnouncement().getExpiration(),
                licence.getId(),
                licence.getCreation(),
                licence.isPremium(),
                licence.getPlan().getId(),
                licence.getPlan().getCost(),
                licence.getPlan().getPremiumCost(),
                licence.getPlan().getDays()
        );
    }
}