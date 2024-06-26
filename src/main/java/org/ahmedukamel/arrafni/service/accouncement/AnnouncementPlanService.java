package org.ahmedukamel.arrafni.service.accouncement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.announcement.AnnouncementPlanResponse;
import org.ahmedukamel.arrafni.dto.announcement.CreateAnnouncementPlanRequest;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.mapper.announcement.AnnouncementPlanResponseMapper;
import org.ahmedukamel.arrafni.model.AnnouncementPlan;
import org.ahmedukamel.arrafni.repository.AnnouncementPlanRepository;
import org.ahmedukamel.arrafni.saver.AnnouncementPlanSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        CreateAnnouncementPlanRequest request = (CreateAnnouncementPlanRequest) object;
        DatabaseService.unique(repository::existsByName, request.name(), AnnouncementPlan.class);
        AnnouncementPlan savedPlan = saver.apply(request);
        AnnouncementPlanResponse response = mapper.apply(savedPlan);
        return new ApiResponse(true, "Successful Create Announcement Plan.", response);
    }

    @Override
    public void setActiveStatus(Integer id, boolean active) {
        AnnouncementPlan plan = DatabaseService.get(repository::findById, id, AnnouncementPlan.class);
        plan.setActive(active);
        repository.save(plan);
    }

    @Transactional
    @Override
    public Object readAnnouncementPlan(Integer id) {
        AnnouncementPlan plan = DatabaseService.get(repository::findById, id, AnnouncementPlan.class);
        AnnouncementPlanResponse response = mapper.apply(plan);
        return new ApiResponse(true, "Successful Get Announcement Plan.", response);
    }

    @Transactional
    @Override
    public Object readAnnouncementPlans(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<AnnouncementPlanResponse> response = repository
                .findAll(pageable)
                .map(mapper);

        return new ApiResponse(true, "Successful Get Announcement Plans.", response);
    }

    @Transactional
    @Override
    public Object getAnnouncementPlan(Integer id) {
        AnnouncementPlan plan = DatabaseService.get(repository::findActiveById, id, AnnouncementPlan.class);
        AnnouncementPlanResponse response = mapper.apply(plan);
        return new ApiResponse(true, "Successful Get Announcement Plan.", response);
    }

    @Transactional
    @Override
    public Object getAnnouncementPlans(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<AnnouncementPlanResponse> response = repository
                .findAllByActiveTrue(pageable)
                .map(mapper);

        return new ApiResponse(true, "Successful Get Announcement Plans.", response);
    }
}