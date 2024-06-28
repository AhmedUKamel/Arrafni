package org.ahmedukamel.arrafni.mapper.offer;

import org.ahmedukamel.arrafni.dto.offer.OfferPlanResponse;
import org.ahmedukamel.arrafni.model.OfferPlan;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class OfferPlanResponseMapper implements Function<OfferPlan, OfferPlanResponse> {
    @Override
    public OfferPlanResponse apply(OfferPlan plan) {
        return new OfferPlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getCost(),
                plan.getDays(),
                plan.isActive(),
                plan.getLicences().size()
        );
    }
}