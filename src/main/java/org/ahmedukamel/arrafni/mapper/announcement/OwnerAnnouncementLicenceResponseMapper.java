package org.ahmedukamel.arrafni.mapper.announcement;

import org.ahmedukamel.arrafni.dto.announcement.OwnerAnnouncementLicenceResponse;
import org.ahmedukamel.arrafni.model.AnnouncementLicence;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@Component
public class OwnerAnnouncementLicenceResponseMapper implements Function<AnnouncementLicence, OwnerAnnouncementLicenceResponse> {
    @Override
    public OwnerAnnouncementLicenceResponse apply(AnnouncementLicence licence) {
        return new OwnerAnnouncementLicenceResponse(
                licence.getId(),
                licence.getCreation(),
                licence.getExpiration(),
                licence.isValid(),
                licence.isManual(),
                licence.getAnnouncement().getId(),
                licence.getPlan().getId(),
                Objects.nonNull(licence.getPayment()) ? licence.getPayment().getId() : null
        );
    }
}