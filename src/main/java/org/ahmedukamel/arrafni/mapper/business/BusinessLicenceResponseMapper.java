package org.ahmedukamel.arrafni.mapper.business;

import org.ahmedukamel.arrafni.dto.business.BusinessLicenceResponse;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.BusinessLicence;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BusinessLicenceResponseMapper implements Function<BusinessLicence, BusinessLicenceResponse> {

    @Override
    public BusinessLicenceResponse apply(BusinessLicence licence) {
        Business business = licence.getBusiness();

        return new BusinessLicenceResponse(
                business.getId(),
                licence.getId(),
                business.getName(),
                business.getDescription(),
                business.getAddress()
        );
    }
}