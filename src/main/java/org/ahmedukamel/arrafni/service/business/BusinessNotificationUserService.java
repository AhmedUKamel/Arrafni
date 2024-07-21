package org.ahmedukamel.arrafni.service.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.business.AddBusinessNotificationRequest;
import org.ahmedukamel.arrafni.dto.business.SendBusinessNotificationRequest;
import org.ahmedukamel.arrafni.mapper.business.AdminBusinessNotificationLicenceResponseMapper;
import org.ahmedukamel.arrafni.mapper.business.BusinessNotificationPlanResponseMapper;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.BusinessNotificationLicence;
import org.ahmedukamel.arrafni.model.BusinessNotificationPlan;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.repository.BusinessNotificationLicenceRepository;
import org.ahmedukamel.arrafni.repository.BusinessNotificationPlanRepository;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.saver.business.SendBusinessNotificationRequestSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BusinessNotificationUserService
        implements IBusinessNotificationUserService {

    private final AdminBusinessNotificationLicenceResponseMapper responseMapper;
    private final SendBusinessNotificationRequestSaver notificationRequestSaver;
    private final BusinessNotificationPlanResponseMapper planResponseMapper;
    private final BusinessNotificationLicenceRepository licenceRepository;
    private final BusinessNotificationPlanRepository planRepository;
    private final BusinessRepository businessRepository;

    @Transactional
    @Override
    public Object sendBusinessNotification(Object object, MultipartFile file) {
        var request = (SendBusinessNotificationRequest) object;

        notificationRequestSaver.accept(request, file);

        String message = "Notification sent successfully";

        return new ApiResponse(true, message, "");
    }

    @Override
    public Object addBusinessNotification(Object object) {
        AddBusinessNotificationRequest request = (AddBusinessNotificationRequest) object;

        Business business = DatabaseService.get(businessRepository::findByIdAndDeletedIsFalse,
                request.businessId(), Business.class);

        BusinessNotificationPlan plan = DatabaseService.get(planRepository::findByIdAndActiveIsTrue,
                request.planId(), BusinessNotificationPlan.class);

        User currentUser = ContextHolderUtils.getUser();

        if (!business.getOwner().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not owner of this business");
        }

        BusinessNotificationLicence licence = new BusinessNotificationLicence();
        licence.setBusiness(business);
        licence.setPlan(plan);

        licenceRepository.save(licence);

        String message = "Licence created successfully";

        return new ApiResponse(true, message, "");
    }

    @Override
    public Object getBusinessNotificationPlan(Integer id) {
        BusinessNotificationPlan plan = DatabaseService.get(planRepository::findByIdAndActiveIsTrue,
                id, BusinessNotificationPlan.class);

        var response = planResponseMapper.apply(plan);
        String message = "Plan retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getBusinessNotificationPlans(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("id").descending());

        Specification<BusinessNotificationPlan> specification =
                (root, query, cb) -> cb.equal(root.get("active"), true);

        var plans = planRepository.findAll(specification, pageable);
        var response = plans.map(planResponseMapper);
        String message = "All plans retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getMyBusinessNotificationLicences(int pageNumber, int pageSize) {
        User user = ContextHolderUtils.getUser();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("id").descending());
        Specification<BusinessNotificationLicence> specification =
                (root, query, cb) -> cb.equal(root.get("business").get("owner").get("id"), user.getId());

        var licences = licenceRepository.findAll(specification, pageable);

        var response = licences.map(responseMapper);
        String message = "All licence retrieved successfully";

        return new ApiResponse(true, message, response);
    }
}
