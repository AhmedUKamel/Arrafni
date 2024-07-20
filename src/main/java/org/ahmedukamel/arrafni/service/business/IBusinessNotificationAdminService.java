package org.ahmedukamel.arrafni.service.business;

public interface IBusinessNotificationAdminService {
    Object getPendingActivationBusinessNotificationLicences(int pageNumber, int pageSize);

    Object activateBusinessNotificationLicences(Long id);

    Object deleteBusinessNotificationLicences(Long id);

    Object createBusinessNotificationPlan(Object object);

    Object setBusinessNotificationPlanActiveStatus(Integer id, Boolean active);

    Object getBusinessNotificationPlans(int pageNumber, int pageSize, Boolean active);
}
