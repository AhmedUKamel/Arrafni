package org.ahmedukamel.arrafni.service.offer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.offer.OfferPlanResponse;
import org.ahmedukamel.arrafni.dto.offer.CreateOfferPlanRequest;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.mapper.offer.OfferPlanResponseMapper;
import org.ahmedukamel.arrafni.model.OfferPlan;
import org.ahmedukamel.arrafni.repository.OfferPlanRepository;
import org.ahmedukamel.arrafni.saver.offer.OfferPlanSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferPlanService implements IOfferPlanService {
    final OfferPlanResponseMapper mapper;
    final OfferPlanRepository repository;
    final OfferPlanSaver saver;

    @Override
    public Object createOfferPlan(Object object) {
        CreateOfferPlanRequest request = (CreateOfferPlanRequest) object;
        DatabaseService.unique(repository::existsByName, request.name(), OfferPlan.class);
        OfferPlan savedPlan = saver.apply(request);
        OfferPlanResponse response = mapper.apply(savedPlan);
        return new ApiResponse(true, "Successful Create Offer Plan.", response);
    }

    @Override
    public void setActiveStatus(Integer id, boolean active) {
        OfferPlan plan = DatabaseService.get(repository::findById, id, OfferPlan.class);
        plan.setActive(active);
        repository.save(plan);
    }

    @Transactional
    @Override
    public Object readOfferPlan(Integer id) {
        OfferPlan plan = DatabaseService.get(repository::findById, id, OfferPlan.class);
        OfferPlanResponse response = mapper.apply(plan);
        return new ApiResponse(true, "Successful Get Offer Plan.", response);
    }

    @Transactional
    @Override
    public Object readOfferPlans(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<OfferPlanResponse> response = repository
                .findAll(pageable)
                .map(mapper);

        return new ApiResponse(true, "Successful Get Offer Plans.", response);
    }

    @Transactional
    @Override
    public Object getOfferPlan(Integer id) {
        OfferPlan plan = DatabaseService.get(repository::findByIdAndActiveIsTrue, id, OfferPlan.class);
        OfferPlanResponse response = mapper.apply(plan);
        return new ApiResponse(true, "Successful Get Offer Plan.", response);
    }

    @Transactional
    @Override
    public Object getOfferPlans(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<OfferPlanResponse> response = repository
                .findAllByActiveIsTrue(pageable)
                .map(mapper);

        return new ApiResponse(true, "Successful Get Offer Plans.", response);
    }
}