package org.ahmedukamel.arrafni.mapper.offer;

import org.ahmedukamel.arrafni.constant.ApiConstants;
import org.ahmedukamel.arrafni.dto.offer.UserOfferResponse;
import org.ahmedukamel.arrafni.model.Offer;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserOfferResponseMapper implements Function<Offer, UserOfferResponse> {
    @Override
    public UserOfferResponse apply(Offer Offer) {
        return new UserOfferResponse(
                Offer.getId(),
                Offer.getBusiness().getId(),
                Offer.getTitle(),
                Offer.getDescription(),
                ApiConstants.OFFER_POSTER_API.formatted(Offer.getId()),
                Offer.getCreation(),
                Offer.getExpiration()
        );
    }
}