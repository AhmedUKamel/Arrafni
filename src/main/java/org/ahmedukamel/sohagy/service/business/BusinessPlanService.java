package org.ahmedukamel.sohagy.service.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.sohagy.dto.business.BusinessPlanResponse;
import org.ahmedukamel.sohagy.dto.business.CreateBusinessPlanRequest;
import org.ahmedukamel.sohagy.dto.response.ApiResponse;
import org.ahmedukamel.sohagy.mapper.business.BusinessPlanResponseMapper;
import org.ahmedukamel.sohagy.model.BusinessPlan;
import org.ahmedukamel.sohagy.repository.BusinessPlanRepository;
import org.ahmedukamel.sohagy.saver.BusinessPlanSaver;
import org.ahmedukamel.sohagy.service.db.DatabaseService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class BusinessPlanService implements IBusinessPlanService {
    final BusinessPlanResponseMapper mapper;
    final BusinessPlanRepository repository;
    final BusinessPlanSaver saver;

    @Override
    public Object createBusinessPlan(Object object) {
        CreateBusinessPlanRequest request = (CreateBusinessPlanRequest) object;
        DatabaseService.unique(repository::existsByName, request.name(), BusinessPlan.class);
        BusinessPlan savedPlan = saver.apply(request);
        BusinessPlanResponse response = mapper.apply(savedPlan);
        return new ApiResponse(true, "Successful Create Business Plan.", response);
    }

    @Override
    public void setActiveStatus(Integer id, boolean active) {
        BusinessPlan plan = DatabaseService.get(repository::findById, id, BusinessPlan.class);
        plan.setActive(active);
        repository.save(plan);
    }

    @Override
    public Object readBusinessPlan(Integer id) {
        BusinessPlan plan = DatabaseService.get(repository::findActiveById, id, BusinessPlan.class);
        BusinessPlanResponse response = mapper.apply(plan);
        return new ApiResponse(true, "Successful Get Business Plan.", response);
    }

    @Override
    public Object readBusinessPlans() {
        Collection<BusinessPlanResponse> response = repository.findAll()
                .stream()
                .filter(BusinessPlan::isActive)
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Get Business Plans.", response);
    }

    @Override
    public Object readBusinessPlans(Integer pageSize, Integer pageNumber) {
        final Collection<BusinessPlanResponse> response;
        if (pageSize < 1 && pageNumber < 1) {
            response = Collections.emptyList();
        } else {
            response = repository.selectPlans(pageNumber, pageSize * (pageNumber - 1))
                    .stream()
                    .filter(BusinessPlan::isActive)
                    .map(mapper)
                    .toList();
        }
        return new ApiResponse(true, "Successful Get Business Plans.", response);
    }
}