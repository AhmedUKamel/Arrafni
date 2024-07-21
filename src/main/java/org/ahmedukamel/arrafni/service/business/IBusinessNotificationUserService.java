package org.ahmedukamel.arrafni.service.business;

import org.springframework.web.multipart.MultipartFile;

public interface IBusinessNotificationUserService {
    Object sendBusinessNotification(Object object, MultipartFile file);

    Object addBusinessNotification(Object object);

    Object getBusinessNotificationPlan(Integer id);

    Object getBusinessNotificationPlans(int pageNumber, int pageSize);

    Object getMyBusinessNotificationLicences(int pageNumber, int pageSize);
}
