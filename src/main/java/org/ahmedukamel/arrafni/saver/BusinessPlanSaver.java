package org.ahmedukamel.arrafni.saver;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.business.CreateBusinessPlanRequest;
import org.ahmedukamel.arrafni.model.BusinessPlan;
import org.ahmedukamel.arrafni.repository.BusinessPlanRepository;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BusinessPlanSaver implements Function<CreateBusinessPlanRequest, BusinessPlan> {
    final BusinessPlanRepository repository;

    @Override
    public BusinessPlan apply(CreateBusinessPlanRequest request) {
        BusinessPlan plan = new BusinessPlan();
        plan.setName(request.name().strip());
        plan.setCost(request.cost());
        plan.setDays(request.days());
        return repository.save(plan);
    }
}