package org.ahmedukamel.arrafni.saver.announcement;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.constant.PathConstants;
import org.ahmedukamel.arrafni.dto.announcement.CreateAnnouncementRequest;
import org.ahmedukamel.arrafni.model.Announcement;
import org.ahmedukamel.arrafni.model.AnnouncementLicence;
import org.ahmedukamel.arrafni.model.AnnouncementPlan;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.repository.AnnouncementPlanRepository;
import org.ahmedukamel.arrafni.repository.AnnouncementRepository;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.saver.FileSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class AnnouncementSaver implements BiFunction<CreateAnnouncementRequest, MultipartFile, Announcement> {
    private final AnnouncementPlanRepository announcementPlanRepository;
    private final AnnouncementRepository announcementRepository;
    private final BusinessRepository businessRepository;
    private final FileSaver fileSaver;

    @Override
    public Announcement apply(CreateAnnouncementRequest request, MultipartFile poster) {
        Business business = DatabaseService.get(businessRepository::findById, request.businessId(), Business.class);

        if (!business.getOwner().getId().equals(ContextHolderUtils.getUser().getId())) {
            throw new IllegalStateException("You are not owner of this business");
        }

        Optional<String> optionalPosterId = fileSaver.apply(poster, PathConstants.ANNOUNCEMENT_POSTER);

        if (optionalPosterId.isEmpty()) {
            throw new IllegalStateException("Could not save announcement poster");
        }

        Announcement announcement = new Announcement();
        announcement.setBusiness(business);
        announcement.setTitle(request.title().strip());
        announcement.setDescription(request.description().strip());
        announcement.setPoster(optionalPosterId.get());

        AnnouncementPlan plan = DatabaseService.get(announcementPlanRepository::findById,
                request.announcementPlanId(), AnnouncementPlan.class);

        AnnouncementLicence licence = new AnnouncementLicence();
        licence.setAnnouncement(announcement);
        licence.setExpiration(LocalDateTime.now().plusDays(plan.getDays()));
        licence.setPlan(plan);

        announcement.setLicences(Set.of(licence));

        return announcementRepository.save(announcement);
    }
}