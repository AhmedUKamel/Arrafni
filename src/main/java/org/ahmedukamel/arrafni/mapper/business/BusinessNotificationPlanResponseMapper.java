package org.ahmedukamel.arrafni.mapper.business;

import org.ahmedukamel.arrafni.dto.business.BusinessNotificationPlanResponse;
import org.ahmedukamel.arrafni.model.BusinessNotificationPlan;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BusinessNotificationPlanResponseMapper
        implements Function<BusinessNotificationPlan, BusinessNotificationPlanResponse> {

    @Override
    public BusinessNotificationPlanResponse apply(BusinessNotificationPlan plan) {
        return new BusinessNotificationPlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getCost(),
                plan.getCount(),
                plan.isActive()
        );
    }
}
