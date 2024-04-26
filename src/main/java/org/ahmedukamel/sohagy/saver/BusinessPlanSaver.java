package org.ahmedukamel.sohagy.saver;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.sohagy.dto.business.CreateBusinessPlanRequest;
import org.ahmedukamel.sohagy.model.BusinessPlan;
import org.ahmedukamel.sohagy.repository.BusinessPlanRepository;
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