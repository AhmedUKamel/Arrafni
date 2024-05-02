package org.ahmedukamel.arrafni.service.accouncement;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.plan.CreatePlanRequest;
import org.ahmedukamel.arrafni.dto.plan.PlanResponse;
import org.ahmedukamel.arrafni.mapper.announcement.AnnouncementPlanResponseMapper;
import org.ahmedukamel.arrafni.model.AnnouncementPlan;
import org.ahmedukamel.arrafni.repository.AnnouncementPlanRepository;
import org.ahmedukamel.arrafni.saver.AnnouncementPlanSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AnnouncementPlanService implements IAnnouncementPlanService {
    final AnnouncementPlanResponseMapper mapper;
    final AnnouncementPlanRepository repository;
    final AnnouncementPlanSaver saver;

    @Override
    public Object createAnnouncementPlan(Object object) {
        CreatePlanRequest request = (CreatePlanRequest) object;
        DatabaseService.unique(repository::existsByName, request.name(), AnnouncementPlan.class);
        AnnouncementPlan savedPlan = saver.apply(request);
        PlanResponse response = mapper.apply(savedPlan);
        return new ApiResponse(true, "Successful Create Announcement Plan.", response);
    }

    @Override
    public void setActiveStatus(Integer id, boolean active) {
        AnnouncementPlan plan = DatabaseService.get(repository::findById, id, AnnouncementPlan.class);
        plan.setActive(active);
        repository.save(plan);
    }

    @Override
    public Object readAnnouncementPlan(Integer id) {
        AnnouncementPlan plan = DatabaseService.get(repository::findById, id, AnnouncementPlan.class);
        PlanResponse response = mapper.apply(plan);
        return new ApiResponse(true, "Successful Get Announcement Plan.", response);
    }

    @Override
    public Object readAnnouncementPlans(long pageSize, long pageNumber) {
        Collection<PlanResponse> response = repository
                .selectPaginatedAnnouncementPlan(pageNumber, pageSize * (pageNumber - 1))
                .stream()
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Get Announcement Plans.", response);
    }

    @Override
    public Object getAnnouncementPlan(Integer id) {
        AnnouncementPlan plan = DatabaseService.get(repository::findActiveById, id, AnnouncementPlan.class);
        PlanResponse response = mapper.apply(plan);
        return new ApiResponse(true, "Successful Get Announcement Plan.", response);
    }

    @Override
    public Object getAnnouncementPlans(long pageSize, long pageNumber) {
        Collection<PlanResponse> response = repository
                .selectPaginatedAnnouncementPlan(pageNumber, pageSize * (pageNumber - 1))
                .stream()
                .filter(AnnouncementPlan::isActive)
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Get Announcement Plans.", response);
    }
}