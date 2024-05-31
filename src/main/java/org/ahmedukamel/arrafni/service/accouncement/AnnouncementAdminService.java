package org.ahmedukamel.arrafni.service.accouncement;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.announcement.AdminAnnouncementLicenceResponse;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.mapper.announcement.AdminAnnouncementLicenceResponseMapper;
import org.ahmedukamel.arrafni.model.Announcement;
import org.ahmedukamel.arrafni.model.AnnouncementLicence;
import org.ahmedukamel.arrafni.model.AnnouncementPlan;
import org.ahmedukamel.arrafni.repository.AnnouncementLicenceRepository;
import org.ahmedukamel.arrafni.repository.AnnouncementRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnnouncementAdminService implements IAnnouncementAdminService {
    private final AdminAnnouncementLicenceResponseMapper adminAnnouncementLicenceResponseMapper;
    private final AnnouncementLicenceRepository announcementLicenceRepository;
    private final AnnouncementRepository announcementRepository;

    @Override
    public Object getPendingAnnouncementLicences(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<AnnouncementLicence> licences = announcementLicenceRepository.findAllByValid(false, pageable);

        Page<AdminAnnouncementLicenceResponse> response = licences.map(adminAnnouncementLicenceResponseMapper);
        String message = "Inactive announcement licences retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object activateAnnouncementLicence(Long id) {
        AnnouncementLicence licence = DatabaseService.get(announcementLicenceRepository::findById, id, AnnouncementLicence.class);

        if (licence.isValid()) {
            throw new IllegalStateException("Licence already activated");
        }

        AnnouncementPlan plan = licence.getPlan();
        Announcement announcement = licence.getAnnouncement();

        announcement.setExpiration(LocalDateTime.now().plusDays(plan.getDays()));
        announcement.setPremium(licence.isPremium());
        announcement.setActive(true);

        licence.setValid(true);
        licence.setManual(true);

        announcementRepository.save(announcement);
        announcementLicenceRepository.save(licence);

        String message = "Licence activated successfully";

        return new ApiResponse(true, message, "");
    }

    @Override
    public Object setAnnouncementBlockStatus(Long id, Boolean blocked) {
        Announcement announcement = DatabaseService.get(announcementRepository::findById, id, Announcement.class);

        announcement.setBlocked(blocked);

        announcementRepository.save(announcement);

        String message = "Announcement block status set successfully";

        return new ApiResponse(true, message, "");
    }
}