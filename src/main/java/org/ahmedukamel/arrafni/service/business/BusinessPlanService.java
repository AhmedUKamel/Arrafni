package org.ahmedukamel.arrafni.service.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.business.BusinessPlanResponse;
import org.ahmedukamel.arrafni.dto.business.CreateBusinessPlanRequest;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.mapper.business.BusinessPlanResponseMapper;
import org.ahmedukamel.arrafni.model.BusinessPlan;
import org.ahmedukamel.arrafni.repository.BusinessPlanRepository;
import org.ahmedukamel.arrafni.saver.BusinessPlanSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
        BusinessPlan plan = DatabaseService.get(repository::findById, id, BusinessPlan.class);
        BusinessPlanResponse response = mapper.apply(plan);
        return new ApiResponse(true, "Successful Get Business Plan.", response);
    }

    @Override
    public Object readBusinessPlans(long pageSize, long pageNumber) {
        Collection<BusinessPlanResponse> response = repository
                .selectPaginatedBusinessPlans(pageNumber, pageSize * (pageNumber - 1))
                .stream()
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Get Business Plans.", response);
    }

    @Override
    public Object getBusinessPlan(Integer id) {
        BusinessPlan plan = DatabaseService.get(repository::findActiveById, id, BusinessPlan.class);
        BusinessPlanResponse response = mapper.apply(plan);
        return new ApiResponse(true, "Successful Get Business Plan.", response);
    }

    @Override
    public Object getBusinessPlans(long pageSize, long pageNumber) {
        Collection<BusinessPlanResponse> response = repository
                .selectPaginatedBusinessPlans(pageNumber, pageSize * (pageNumber - 1))
                .stream()
                .filter(BusinessPlan::isActive)
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Get Business Plans.", response);
    }
}