package org.ahmedukamel.arrafni.service.accouncement;

import org.springframework.web.multipart.MultipartFile;

public interface IAnnouncementManagementService {
    Object createAnnouncement(Object object, MultipartFile poster);

    void deleteAnnouncement(Long id);

    Object renewAnnouncement(Object object);

    Object getAnnouncementLicences(Long id);

    Object getAnnouncement(Long id);

    Object getAnnouncements(int pageSize, int pageNumber);

    Object getBusinessAnnouncements(Long id, int pageSize, int pageNumber);
}