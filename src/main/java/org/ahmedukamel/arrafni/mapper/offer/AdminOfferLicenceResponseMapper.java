package org.ahmedukamel.arrafni.mapper.offer;

import org.ahmedukamel.arrafni.dto.offer.AdminOfferLicenceResponse;
import org.ahmedukamel.arrafni.model.OfferLicence;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AdminOfferLicenceResponseMapper
        implements Function<OfferLicence, AdminOfferLicenceResponse> {

    @Override
    public AdminOfferLicenceResponse apply(OfferLicence licence) {
        return new AdminOfferLicenceResponse(
                licence.getOffer().getId(),
                licence.getOffer().isActive(),
                licence.getOffer().isBlocked(),
                licence.getOffer().isDeleted(),
                licence.getOffer().getCreation(),
                licence.getOffer().getExpiration(),
                licence.getId(),
                licence.getCreation(),
                licence.getPlan().getId(),
                licence.getPlan().getCost(),
                licence.getPlan().getDays()
        );
    }
}