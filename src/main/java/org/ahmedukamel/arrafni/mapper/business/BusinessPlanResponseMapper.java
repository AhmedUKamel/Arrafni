package org.ahmedukamel.arrafni.mapper.business;

import org.ahmedukamel.arrafni.dto.business.BusinessPlanResponse;
import org.ahmedukamel.arrafni.model.BusinessPlan;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BusinessPlanResponseMapper implements Function<BusinessPlan, BusinessPlanResponse> {
    @Override
    public BusinessPlanResponse apply(BusinessPlan plan) {
        return new BusinessPlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getCost(),
                plan.getDays(),
                plan.isActive(),
                plan.getLicences().size()
        );
    }
}