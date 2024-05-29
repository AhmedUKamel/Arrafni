package org.ahmedukamel.arrafni.service.accouncement;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.announcement.CreateAnnouncementRequest;
import org.ahmedukamel.arrafni.dto.announcement.OwnerAnnouncementLicenceResponse;
import org.ahmedukamel.arrafni.dto.announcement.OwnerAnnouncementResponse;
import org.ahmedukamel.arrafni.dto.announcement.RenewAnnouncementRequest;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.mapper.announcement.OwnerAnnouncementLicenceResponseMapper;
import org.ahmedukamel.arrafni.mapper.announcement.OwnerAnnouncementResponseMapper;
import org.ahmedukamel.arrafni.model.Announcement;
import org.ahmedukamel.arrafni.model.AnnouncementLicence;
import org.ahmedukamel.arrafni.model.AnnouncementPlan;
import org.ahmedukamel.arrafni.repository.AnnouncementPlanRepository;
import org.ahmedukamel.arrafni.repository.AnnouncementRepository;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.saver.announcement.AnnouncementSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AnnouncementManagementService implements IAnnouncementManagementService {
    private final OwnerAnnouncementLicenceResponseMapper ownerAnnouncementLicenceResponseMapper;
    private final OwnerAnnouncementResponseMapper ownerAnnouncementResponseMapper;
    private final AnnouncementPlanRepository announcementPlanRepository;
    private final AnnouncementRepository announcementRepository;
    private final BusinessRepository businessRepository;
    private final AnnouncementSaver announcementSaver;

    @Override
    public Object createAnnouncement(Object object, MultipartFile poster) {
        CreateAnnouncementRequest request = (CreateAnnouncementRequest) object;

        Announcement savedAnnouncement = announcementSaver.apply(request, poster);

        OwnerAnnouncementResponse response = ownerAnnouncementResponseMapper.apply(savedAnnouncement);
        String message = "Announcement created successfully.";

        return new ApiResponse(true, message, response);
    }

    @Override
    public void deleteAnnouncement(Long id) {
        Announcement announcement = getAnnouncement(id);

        announcement.setDeleted(true);
        announcementRepository.save(announcement);
    }

    @Override
    public Object renewAnnouncement(Object object) {
        RenewAnnouncementRequest request = (RenewAnnouncementRequest) object;

        Announcement announcement = getAnnouncement(request.announcementId());

        if (announcement.getExpiration().isAfter(LocalDateTime.now())) {
            throw new IllegalStateException("Announcement already active.");
        }

        AnnouncementPlan plan = DatabaseService.get(announcementPlanRepository::findById,
                request.announcementPlanId(), AnnouncementPlan.class);

        AnnouncementLicence licence = new AnnouncementLicence();
        licence.setAnnouncement(announcement);
        licence.setPlan(plan);
        licence.setExpiration(LocalDateTime.now().plusDays(plan.getDays()));

        announcement.getLicences().add(licence);
        announcementRepository.save(announcement);

        String message = "Announcement renewed successfully.";

        return new ApiResponse(true, message, "");
    }

    @Override
    public Object getAnnouncementLicences(Long id) {
        Announcement announcement = getAnnouncement(id);

        Collection<AnnouncementLicence> licences = announcement.getLicences();

        Collection<OwnerAnnouncementLicenceResponse> response = licences
                .stream()
                .map(ownerAnnouncementLicenceResponseMapper)
                .toList();
        String message = "Announcement licences retrieved successfully.";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getAnnouncements(long pageSize, long pageNumber) {
        return null;
    }

    @Override
    public Object getBusinessAnnouncements(Long id, long pageSize, long pageNumber) {
        return null;
    }

    private Announcement getAnnouncement(Long id) {
        Announcement announcement = DatabaseService.get(announcementRepository::findById, id, Announcement.class);

        if (!announcement.getBusiness().getOwner().getId().equals(ContextHolderUtils.getUser().getId())) {
            throw new IllegalStateException("You are not owner of this announcement.");
        }
        return announcement;
    }
}
