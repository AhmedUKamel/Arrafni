package org.ahmedukamel.arrafni.mapper.announcement;

import org.ahmedukamel.arrafni.constant.ApiConstants;
import org.ahmedukamel.arrafni.dto.announcement.UserAnnouncementResponse;
import org.ahmedukamel.arrafni.model.Announcement;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserAnnouncementResponseMapper implements Function<Announcement, UserAnnouncementResponse> {
    @Override
    public UserAnnouncementResponse apply(Announcement announcement) {
        return new UserAnnouncementResponse(
                announcement.getId(),
                announcement.getBusiness().getId(),
                announcement.getTitle(),
                announcement.getDescription(),
                ApiConstants.ANNOUNCEMENT_POSTER_API.formatted(announcement.getId()),
                announcement.getCreation(),
                announcement.getExpiration()
        );
    }
}