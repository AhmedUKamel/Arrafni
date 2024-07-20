package org.ahmedukamel.arrafni.mapper.business;

import org.ahmedukamel.arrafni.constant.ApiConstants;
import org.ahmedukamel.arrafni.dto.business.AdminBusinessNotificationLicenceResponse;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.BusinessNotificationLicence;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AdminBusinessNotificationLicenceResponseMapper
        implements Function<BusinessNotificationLicence, AdminBusinessNotificationLicenceResponse> {

    @Override
    public AdminBusinessNotificationLicenceResponse apply(BusinessNotificationLicence licence) {
        Business business = licence.getBusiness();
        return new AdminBusinessNotificationLicenceResponse(
                licence.getId(),
                business.getId(),
                business.getName(),
                business.getDescription(),
                business.getAddress(),
                business.getRegion().getName(),
                ApiConstants.BUSINESS_LOGO_API.formatted(business.getLogo()),
                licence.getCreation(),
                licence.isValid(),
                licence.isManual()
        );
    }
}
