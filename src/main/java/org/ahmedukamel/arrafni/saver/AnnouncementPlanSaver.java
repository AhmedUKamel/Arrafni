package org.ahmedukamel.arrafni.saver;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.plan.CreatePlanRequest;
import org.ahmedukamel.arrafni.model.AnnouncementPlan;
import org.ahmedukamel.arrafni.repository.AnnouncementPlanRepository;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class AnnouncementPlanSaver implements Function<CreatePlanRequest, AnnouncementPlan> {
    final AnnouncementPlanRepository repository;

    @Override
    public AnnouncementPlan apply(CreatePlanRequest request) {
        AnnouncementPlan plan = new AnnouncementPlan();
        plan.setName(request.name().strip());
        plan.setCost(request.cost());
        plan.setDays(request.days());
        return repository.save(plan);
    }
}