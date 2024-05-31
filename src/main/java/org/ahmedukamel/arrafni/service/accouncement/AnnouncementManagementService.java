package org.ahmedukamel.arrafni.service.accouncement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.announcement.CreateAnnouncementRequest;
import org.ahmedukamel.arrafni.dto.announcement.OwnerAnnouncementLicenceResponse;
import org.ahmedukamel.arrafni.dto.announcement.OwnerAnnouncementResponse;
import org.ahmedukamel.arrafni.dto.announcement.RenewAnnouncementRequest;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.mapper.announcement.OwnerAnnouncementLicenceResponseMapper;
import org.ahmedukamel.arrafni.mapper.announcement.OwnerAnnouncementResponseMapper;
import org.ahmedukamel.arrafni.model.*;
import org.ahmedukamel.arrafni.repository.AnnouncementPlanRepository;
import org.ahmedukamel.arrafni.repository.AnnouncementRepository;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.saver.announcement.AnnouncementSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

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
        Announcement announcement = getUserAnnouncement(id);

        announcement.setDeleted(true);
        announcementRepository.save(announcement);
    }

    @Transactional
    @Override
    public Object renewAnnouncement(Object object) {
        RenewAnnouncementRequest request = (RenewAnnouncementRequest) object;

        Announcement announcement = getUserAnnouncement(request.announcementId());

        if (Objects.nonNull(announcement.getExpiration()) && announcement.getExpiration().isAfter(LocalDateTime.now())) {
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

    @Transactional
    @Override
    public Object getAnnouncementLicences(Long id) {
        Announcement announcement = getUserAnnouncement(id);

        Collection<AnnouncementLicence> licences = announcement.getLicences();

        Collection<OwnerAnnouncementLicenceResponse> response = licences
                .stream()
                .map(ownerAnnouncementLicenceResponseMapper)
                .toList();
        String message = "Announcement licences retrieved successfully.";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getAnnouncement(Long id) {
        Announcement announcement = getUserAnnouncement(id);

        OwnerAnnouncementResponse response = ownerAnnouncementResponseMapper.apply(announcement);
        String message = "Announcement retrieved successfully.";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getAnnouncements(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        User user = ContextHolderUtils.getUser();

        Page<Announcement> announcements = announcementRepository.findAllByBusiness_Owner_Id(user.getId(), pageable);

        Page<OwnerAnnouncementResponse> response = announcements.map(ownerAnnouncementResponseMapper);
        String message = "Announcement retrieved successfully.";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getBusinessAnnouncements(Long id, int pageSize, int pageNumber) {
        Business business = getUserBusiness(id);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Announcement> announcements = announcementRepository.findAllByBusiness_Id(business.getId(), pageable);

        Page<OwnerAnnouncementResponse> response = announcements.map(ownerAnnouncementResponseMapper);
        String message = "Announcement retrieved successfully.";

        return new ApiResponse(true, message, response);
    }

    private Announcement getUserAnnouncement(Long id) {
        Announcement announcement = DatabaseService.get(announcementRepository::findById, id, Announcement.class);

        if (!announcement.getBusiness().getOwner().getId().equals(ContextHolderUtils.getUser().getId())) {
            throw new IllegalStateException("You are not owner of this announcement.");
        }

        return announcement;
    }

    private Business getUserBusiness(Long id) {
        Business business = DatabaseService.get(businessRepository::findById, id, Business.class);

        if (!business.getOwner().getId().equals(ContextHolderUtils.getUser().getId())) {
            throw new IllegalStateException("You are not owner of this business.");
        }

        return business;
    }
}
