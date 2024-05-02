package org.ahmedukamel.arrafni.saver;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.plan.CreatePlanRequest;
import org.ahmedukamel.arrafni.model.BusinessPlan;
import org.ahmedukamel.arrafni.repository.BusinessPlanRepository;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BusinessPlanSaver implements Function<CreatePlanRequest, BusinessPlan> {
    final BusinessPlanRepository repository;

    @Override
    public BusinessPlan apply(CreatePlanRequest request) {
        BusinessPlan plan = new BusinessPlan();
        plan.setName(request.name().strip());
        plan.setCost(request.cost());
        plan.setDays(request.days());
        return repository.save(plan);
    }
}