package org.ahmedukamel.arrafni.mapper.offer;

import org.ahmedukamel.arrafni.dto.offer.OwnerOfferLicenceResponse;
import org.ahmedukamel.arrafni.model.OfferLicence;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@Component
public class OwnerOfferLicenceResponseMapper implements Function<OfferLicence, OwnerOfferLicenceResponse> {
    @Override
    public OwnerOfferLicenceResponse apply(OfferLicence licence) {
        return new OwnerOfferLicenceResponse(
                licence.getId(),
                licence.getCreation(),
                licence.getExpiration(),
                licence.isValid(),
                licence.isManual(),
                licence.getOffer().getId(),
                licence.getPlan().getId(),
                Objects.nonNull(licence.getPayment()) ? licence.getPayment().getId() : null
        );
    }
}