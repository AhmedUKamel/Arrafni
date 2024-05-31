package org.ahmedukamel.arrafni.service.accouncement;

public interface IAnnouncementUserService {
    Object getPremiumAnnouncements(int pageSize, int pageNumber);

    Object getAllAnnouncements(int pageSize, int pageNumber);
}