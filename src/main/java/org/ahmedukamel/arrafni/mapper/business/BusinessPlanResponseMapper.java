package org.ahmedukamel.arrafni.mapper.business;

import org.ahmedukamel.arrafni.dto.plan.PlanResponse;
import org.ahmedukamel.arrafni.model.BusinessPlan;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BusinessPlanResponseMapper implements Function<BusinessPlan, PlanResponse> {
    @Override
    public PlanResponse apply(BusinessPlan plan) {
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