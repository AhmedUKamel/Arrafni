package org.ahmedukamel.arrafni.service.accouncement;

public interface IAnnouncementAdminService {
    Object getPendingAnnouncementLicences(int pageSize, int pageNumber);

    Object activateAnnouncementLicence(Long id);

    Object setAnnouncementBlockStatus(Long id, Boolean blocked);
}