package org.ahmedukamel.arrafni.service.accouncement;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.model.Announcement;
import org.ahmedukamel.arrafni.model.AnnouncementPlan;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.repository.AnnouncementPlanRepository;
import org.ahmedukamel.arrafni.repository.AnnouncementRepository;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AnnouncementManagementService implements IAnnouncementManagementService {
    final AnnouncementPlanRepository announcementPlanRepository;
    final AnnouncementRepository repository;
    final BusinessRepository businessRepository;

    @Override
    public Object createAnnouncement(String title, String description, MultipartFile poster, Long businessId, Integer announcementPlanId) {
        Business business = DatabaseService.get(businessRepository::findById, businessId, Business.class);
        AnnouncementPlan plan = DatabaseService.get(announcementPlanRepository::findById, announcementPlanId, AnnouncementPlan.class);
        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setDescription(description);
        announcement.setBusiness(business);
        return null;
    }

    @Override
    public void deleteAnnouncement(Long id) {

    }

    @Override
    public Object renewAnnouncement(Long id, Integer announcementPlanId) {
        return null;
    }
}
