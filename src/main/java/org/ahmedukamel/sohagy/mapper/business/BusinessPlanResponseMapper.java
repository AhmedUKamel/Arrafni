package org.ahmedukamel.sohagy.mapper.business;

import org.ahmedukamel.sohagy.dto.business.BusinessPlanResponse;
import org.ahmedukamel.sohagy.model.BusinessPlan;
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