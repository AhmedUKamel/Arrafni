package org.ahmedukamel.arrafni.service.accouncement;

public interface IAnnouncementUserService {
    Object getPremiumAnnouncements(int pageSize, int pageNumber);

    Object getAllAnnouncements(int pageSize, int pageNumber);

    Object getSubCategoryAnnouncements(Integer subCategoryId, int pageSize, int pageNumber);

    Object getMainCategoryAnnouncements(Integer mainCategoryId, int pageSize, int pageNumber);
}