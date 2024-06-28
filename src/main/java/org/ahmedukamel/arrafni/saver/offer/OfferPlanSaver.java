package org.ahmedukamel.arrafni.saver.offer;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.offer.CreateOfferPlanRequest;
import org.ahmedukamel.arrafni.model.OfferPlan;
import org.ahmedukamel.arrafni.repository.OfferPlanRepository;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OfferPlanSaver implements Function<CreateOfferPlanRequest, OfferPlan> {
    final OfferPlanRepository repository;

    @Override
    public OfferPlan apply(CreateOfferPlanRequest request) {
        OfferPlan OfferPlan = new OfferPlan();
        OfferPlan.setName(request.name().strip());
        OfferPlan.setCost(request.cost());
        OfferPlan.setDays(request.days());
        return repository.save(OfferPlan);
    }
}