package org.ahmedukamel.arrafni.service.accouncement;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.announcement.UserAnnouncementResponse;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.mapper.announcement.UserAnnouncementResponseMapper;
import org.ahmedukamel.arrafni.model.Announcement;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.repository.AnnouncementRepository;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnnouncementUserService implements IAnnouncementUserService {
    private final UserAnnouncementResponseMapper userAnnouncementResponseMapper;
    private final AnnouncementRepository announcementRepository;

    @Override
    public Object getPremiumAnnouncements(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        User user = ContextHolderUtils.getUser();

        Page<Announcement> announcements = announcementRepository
                .findAllByBlockedAndActiveAndDeletedAndPremiumAndBusiness_Region_Id
                        (false, true, false, true, user.getRegion().getId(), pageable);

        Page<UserAnnouncementResponse> response = announcements.map(userAnnouncementResponseMapper);
        String message = "Announcements retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getAllAnnouncements(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        User user = ContextHolderUtils.getUser();

        Page<Announcement> announcements = announcementRepository
                .findAllByBlockedAndActiveAndDeletedAndBusiness_Region_Id
                        (false, true, false, user.getRegion().getId(), pageable);

        Page<UserAnnouncementResponse> response = announcements.map(userAnnouncementResponseMapper);
        String message = "Announcements retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getSubCategoryAnnouncements(Integer subCategoryId, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        User user = ContextHolderUtils.getUser();

        Page<Announcement> announcements = announcementRepository
                .findAllBySubCategoryAndRegion
                        (subCategoryId, user.getRegion().getId(), pageable);

        Page<UserAnnouncementResponse> response = announcements.map(userAnnouncementResponseMapper);
        String message = "Announcements retrieved successfully";

        return new ApiResponse(true, message, response);
    }
}