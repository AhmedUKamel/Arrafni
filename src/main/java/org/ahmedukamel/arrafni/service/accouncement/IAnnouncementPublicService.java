package org.ahmedukamel.arrafni.service.accouncement;

public interface IAnnouncementPublicService {
    Object getPremiumAnnouncements(Integer regionId, int pageSize, int pageNumber);

    Object getAllAnnouncements(Integer regionId, int pageSize, int pageNumber);

    Object getSubCategoryAnnouncements(Integer regionId, Integer subCategoryId, int pageSize, int pageNumber);

    Object getMainCategoryAnnouncements(Integer regionId, Integer mainCategoryId, int pageSize, int pageNumber);

    Object getBusinessAnnouncements(Long id, int pageSize, int pageNumber);

    Object getAnnouncementById(Long id);

    byte[] getAnnouncementPosters(Long id);
}