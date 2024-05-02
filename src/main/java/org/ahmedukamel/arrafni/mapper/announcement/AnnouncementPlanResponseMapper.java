package org.ahmedukamel.arrafni.mapper.announcement;

import org.ahmedukamel.arrafni.dto.plan.PlanResponse;
import org.ahmedukamel.arrafni.model.AnnouncementPlan;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AnnouncementPlanResponseMapper implements Function<AnnouncementPlan, PlanResponse> {
    @Override
    public PlanResponse apply(AnnouncementPlan plan) {
        return new PlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getCost(),
                plan.getDays(),
                plan.isActive(),
                plan.getLicences().size()
        );
    }
}