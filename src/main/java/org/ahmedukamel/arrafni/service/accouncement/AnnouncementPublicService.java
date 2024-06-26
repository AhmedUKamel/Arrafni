package org.ahmedukamel.arrafni.service.accouncement;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.constant.PathConstants;
import org.ahmedukamel.arrafni.dto.announcement.UserAnnouncementResponse;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.mapper.announcement.UserAnnouncementResponseMapper;
import org.ahmedukamel.arrafni.model.Announcement;
import org.ahmedukamel.arrafni.repository.AnnouncementRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class AnnouncementPublicService implements IAnnouncementPublicService {
    private final UserAnnouncementResponseMapper userAnnouncementResponseMapper;
    private final AnnouncementRepository announcementRepository;

    @Override
    public Object getPremiumAnnouncements(Integer regionId, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Announcement> announcements = announcementRepository
                .findAllByBlockedAndActiveAndDeletedAndPremiumAndBusiness_Region_Id
                        (false, true, false, true, regionId, pageable);

        Page<UserAnnouncementResponse> response = announcements.map(userAnnouncementResponseMapper);
        String message = "Announcements retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getAllAnnouncements(Integer regionId, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Announcement> announcements = announcementRepository
                .findAllByBlockedAndActiveAndDeletedAndPremiumAndBusiness_Region_Id
                        (false, true, false, false, regionId, pageable);

        Page<UserAnnouncementResponse> response = announcements.map(userAnnouncementResponseMapper);
        String message = "Announcements retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getSubCategoryAnnouncements(Integer regionId, Integer subCategoryId, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Announcement> announcements = announcementRepository
                .findAllBySubCategoryAndRegion
                        (subCategoryId, regionId, pageable);

        Page<UserAnnouncementResponse> response = announcements.map(userAnnouncementResponseMapper);
        String message = "Announcements retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getMainCategoryAnnouncements(Integer regionId, Integer mainCategoryId, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Announcement> announcements = announcementRepository
                .findAllByMainCategoryAndRegion
                        (mainCategoryId, regionId, pageable);

        Page<UserAnnouncementResponse> response = announcements.map(userAnnouncementResponseMapper);
        String message = "Announcements retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getBusinessAnnouncements(Long id, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Announcement> announcements = announcementRepository
                .findAllByBlockedAndActiveAndDeletedAndBusiness_Id
                        (false, true, false, id, pageable);

        Page<UserAnnouncementResponse> response = announcements.map(userAnnouncementResponseMapper);
        String message = "Announcements retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getAnnouncementById(Long id) {
        Announcement announcement = DatabaseService.get(announcementRepository::findActive, id, Announcement.class);

        UserAnnouncementResponse response = userAnnouncementResponseMapper.apply(announcement);
        String message = "Announcement retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public byte[] getAnnouncementPosters(Long id) {
        Announcement announcement = DatabaseService.get(announcementRepository::findActive, id, Announcement.class);

        try {
            String filename = announcement.getPoster();

            Path filepath = PathConstants.ANNOUNCEMENT_POSTER.resolve(filename);

            return Files.readAllBytes(filepath);

        } catch (IOException exception) {
            throw new RuntimeException("Failed load announcement poster.", exception);
        }
    }
}
