package org.ahmedukamel.arrafni.service.accouncement;

import org.springframework.web.multipart.MultipartFile;

public interface IAnnouncementManagementService {
    Object createAnnouncement(String title, String description, MultipartFile poster, Long businessId, Integer announcementPlanId);

    void deleteAnnouncement(Long id);

    Object renewAnnouncement(Long id, Integer announcementPlanId);
}