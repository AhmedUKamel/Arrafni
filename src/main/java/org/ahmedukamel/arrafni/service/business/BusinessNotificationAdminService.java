package org.ahmedukamel.arrafni.service.business;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.business.CreateBusinessNotificationPlanRequest;
import org.ahmedukamel.arrafni.mapper.business.AdminBusinessNotificationLicenceResponseMapper;
import org.ahmedukamel.arrafni.mapper.business.BusinessNotificationPlanResponseMapper;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.BusinessNotificationLicence;
import org.ahmedukamel.arrafni.model.BusinessNotificationPlan;
import org.ahmedukamel.arrafni.repository.BusinessNotificationLicenceRepository;
import org.ahmedukamel.arrafni.repository.BusinessNotificationPlanRepository;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.saver.business.CreateBusinessNotificationPlanRequestSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BusinessNotificationAdminService
        implements IBusinessNotificationAdminService {

    private final AdminBusinessNotificationLicenceResponseMapper responseMapper;
    private final CreateBusinessNotificationPlanRequestSaver planRequestSaver;
    private final BusinessNotificationPlanResponseMapper planResponseMapper;
    private final BusinessNotificationLicenceRepository licenceRepository;
    private final BusinessNotificationPlanRepository planRepository;
    private final BusinessRepository businessRepository;

    @Override
    public Object getPendingActivationBusinessNotificationLicences(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("id").descending());
        Specification<BusinessNotificationLicence> specification =
                (root, query, cb) -> cb.equal(root.get("valid"), false);

        var licences = licenceRepository.findAll(specification, pageable);

        var response = licences.map(responseMapper);
        String message = "All licence retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object activateBusinessNotificationLicences(Long id) {
        var licence = DatabaseService.get(licenceRepository::findByIdAndValidIsFalse,
                id, BusinessNotificationLicence.class);

        Business business = licence.getBusiness();
        business.incrementNotificationCount(licence.getPlan().getCount());
        businessRepository.save(business);

        licence.setValid(true);
        licence.setManual(true);
        licenceRepository.save(licence);

        String message = "Licence activated successfully";

        return new ApiResponse(true, message, "");
    }

    @Override
    public Object deleteBusinessNotificationLicences(Long id) {
        var licence = DatabaseService.get(licenceRepository::findByIdAndValidIsFalse,
                id, BusinessNotificationLicence.class);

        licenceRepository.delete(licence);

        String message = "Licence deleted successfully";

        return new ApiResponse(true, message, "");
    }

    @Override
    public Object createBusinessNotificationPlan(Object object) {
        var request = (CreateBusinessNotificationPlanRequest) object;

        var savedPlan = planRequestSaver.apply(request);

        var response = planResponseMapper.apply(savedPlan);
        String message = "Plan retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object setBusinessNotificationPlanActiveStatus(Integer id, Boolean active) {
        var plan = DatabaseService.get(planRepository::findById, id, BusinessNotificationPlan.class);

        plan.setActive(active);

        planRepository.save(plan);

        var response = planResponseMapper.apply(plan);
        String message = "Plan activated successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getBusinessNotificationPlans(int pageNumber, int pageSize, Boolean active) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("id").descending());
        Specification<BusinessNotificationPlan> specification = (root, query, cb) -> Objects.nonNull(active)
                ? cb.equal(root.get("active"), active)
                : cb.conjunction();

        var plans = planRepository.findAll(specification, pageable);

        var response = plans.map(planResponseMapper);
        String message = "Plans retrieved successfully";

        return new ApiResponse(true, message, response);
    }
}
