package org.ahmedukamel.arrafni.mapper.announcement;

import org.ahmedukamel.arrafni.dto.announcement.AnnouncementPlanResponse;
import org.ahmedukamel.arrafni.model.AnnouncementPlan;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AnnouncementPlanResponseMapper implements Function<AnnouncementPlan, AnnouncementPlanResponse> {
    @Override
    public AnnouncementPlanResponse apply(AnnouncementPlan plan) {
        return new AnnouncementPlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getCost(),
                plan.getPremiumCost(),
                plan.getDays(),
                plan.isActive(),
                plan.getLicences().size()
        );
    }
}