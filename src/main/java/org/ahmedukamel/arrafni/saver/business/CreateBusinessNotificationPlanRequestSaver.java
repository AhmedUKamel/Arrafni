package org.ahmedukamel.arrafni.saver.business;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.business.CreateBusinessNotificationPlanRequest;
import org.ahmedukamel.arrafni.model.BusinessNotificationPlan;
import org.ahmedukamel.arrafni.repository.BusinessNotificationPlanRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CreateBusinessNotificationPlanRequestSaver
        implements Function<CreateBusinessNotificationPlanRequest, BusinessNotificationPlan> {

    private final BusinessNotificationPlanRepository planRepository;

    @Override
    public BusinessNotificationPlan apply(CreateBusinessNotificationPlanRequest request) {
        DatabaseService.unique(planRepository::existsByName, request.name().strip(), BusinessNotificationPlan.class);

        var plan = BusinessNotificationPlan
                .builder()
                .name(request.name().strip())
                .count(request.count())
                .cost(request.cost())
                .active(true)
                .build();

        return planRepository.save(plan);
    }
}
