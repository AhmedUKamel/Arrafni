package org.ahmedukamel.arrafni.service.schedule;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.repository.AnnouncementRepository;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// Runs every hour at minute 0
public class ScheduleService {
    private final AnnouncementRepository announcementRepository;
    private final BusinessRepository businessRepository;

    @Scheduled(cron = "0 0 * * * ?")
    public void checkAndDeactivateExpiredBusinesses() {
        businessRepository.deactivateExpiredBusinesses();
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void checkAndDeactivateExpiredAnnouncements() {
        announcementRepository.deactivateExpiredAnnouncements();
    }
}