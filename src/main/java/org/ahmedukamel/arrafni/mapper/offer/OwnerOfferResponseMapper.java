package org.ahmedukamel.arrafni.mapper.offer;

import org.ahmedukamel.arrafni.constant.ApiConstants;
import org.ahmedukamel.arrafni.dto.offer.OwnerOfferResponse;
import org.ahmedukamel.arrafni.model.Offer;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class OwnerOfferResponseMapper implements Function<Offer, OwnerOfferResponse> {
    @Override
    public OwnerOfferResponse apply(Offer offer) {
        return new OwnerOfferResponse(
                offer.getId(),
                offer.getTitle(),
                offer.getDescription(),
                ApiConstants.OFFER_POSTER_API.formatted(offer.getId()),
                offer.getCreation(),
                offer.getExpiration(),
                offer.isActive(),
                offer.isBlocked(),
                offer.isDeleted(),
                offer.getBusiness().getId()
        );
    }
}