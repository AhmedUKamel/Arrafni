package org.ahmedukamel.arrafni.mapper.offer;

import org.ahmedukamel.arrafni.constant.ApiConstants;
import org.ahmedukamel.arrafni.dto.offer.OwnerOfferResponse;
import org.ahmedukamel.arrafni.model.Offer;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class OwnerOfferResponseMapper implements Function<Offer, OwnerOfferResponse> {
    @Override
    public OwnerOfferResponse apply(Offer Offer) {
        return new OwnerOfferResponse(
                Offer.getId(),
                Offer.getTitle(),
                Offer.getDescription(),
                ApiConstants.OFFER_POSTER_API.formatted(Offer.getId()),
                Offer.getCreation(),
                Offer.getExpiration(),
                Offer.isActive(),
                Offer.isBlocked(),
                Offer.getBusiness().getId()
        );
    }
}