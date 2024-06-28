package org.ahmedukamel.arrafni.service.offer;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.offer.UserOfferResponse;
import org.ahmedukamel.arrafni.mapper.offer.UserOfferResponseMapper;
import org.ahmedukamel.arrafni.model.Offer;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.repository.OfferRepository;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferUserService implements IOfferUserService {
    private final UserOfferResponseMapper userOfferResponseMapper;
    private final OfferRepository OfferRepository;

    @Override
    public Object getAllOffers(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        User user = ContextHolderUtils.getUser();

        Page<Offer> offers = OfferRepository
                .findAllByBlockedAndActiveAndDeletedAndBusiness_Region_Id
                        (false, true, false, user.getRegion().getId(), pageable);

        Page<UserOfferResponse> response = offers.map(userOfferResponseMapper);
        String message = "Offers retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getSubCategoryOffers(Integer subCategoryId, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        User user = ContextHolderUtils.getUser();

        Page<Offer> offers = OfferRepository
                .findAllBySubCategoryAndRegion
                        (subCategoryId, user.getRegion().getId(), pageable);

        Page<UserOfferResponse> response = offers.map(userOfferResponseMapper);
        String message = "Offers retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getMainCategoryOffers(Integer mainCategoryId, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        User user = ContextHolderUtils.getUser();

        Page<Offer> offers = OfferRepository
                .findAllByMainCategoryAndRegion
                        (mainCategoryId, user.getRegion().getId(), pageable);

        Page<UserOfferResponse> response = offers.map(userOfferResponseMapper);
        String message = "Offers retrieved successfully";

        return new ApiResponse(true, message, response);
    }
}