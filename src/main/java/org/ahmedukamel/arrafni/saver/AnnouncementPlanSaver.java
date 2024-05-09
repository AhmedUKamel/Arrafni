package org.ahmedukamel.arrafni.saver;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.announcement.CreateAnnouncementPlanRequest;
import org.ahmedukamel.arrafni.model.AnnouncementPlan;
import org.ahmedukamel.arrafni.repository.AnnouncementPlanRepository;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class AnnouncementPlanSaver implements Function<CreateAnnouncementPlanRequest, AnnouncementPlan> {
    final AnnouncementPlanRepository repository;

    @Override
    public AnnouncementPlan apply(CreateAnnouncementPlanRequest request) {
        AnnouncementPlan announcementPlan = new AnnouncementPlan();
        announcementPlan.setName(request.name().strip());
        announcementPlan.setCost(request.cost());
        announcementPlan.setDays(request.days());
        announcementPlan.setPremiumCost(request.premiumCost());
        return repository.save(announcementPlan);
    }
}