package org.ahmedukamel.arrafni.service.offer;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.constant.PathConstants;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.offer.UserOfferResponse;
import org.ahmedukamel.arrafni.mapper.offer.UserOfferResponseMapper;
import org.ahmedukamel.arrafni.model.Offer;
import org.ahmedukamel.arrafni.repository.OfferRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class OfferPublicService implements IOfferPublicService {
    private final UserOfferResponseMapper userOfferResponseMapper;
    private final OfferRepository OfferRepository;

    @Override
    public Object getAllOffers(Integer regionId, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Offer> offers = OfferRepository
                .findAllByBlockedAndActiveAndDeletedAndBusiness_Region_Id
                        (false, true, false, regionId, pageable);

        Page<UserOfferResponse> response = offers.map(userOfferResponseMapper);
        String message = "Offers retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getSubCategoryOffers(Integer regionId, Integer subCategoryId, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Offer> offers = OfferRepository
                .findAllBySubCategoryAndRegion
                        (subCategoryId, regionId, pageable);

        Page<UserOfferResponse> response = offers.map(userOfferResponseMapper);
        String message = "Offers retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getMainCategoryOffers(Integer regionId, Integer mainCategoryId, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Offer> offers = OfferRepository
                .findAllByMainCategoryAndRegion
                        (mainCategoryId, regionId, pageable);

        Page<UserOfferResponse> response = offers.map(userOfferResponseMapper);
        String message = "Offers retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getBusinessOffers(Long id, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Offer> offers = OfferRepository
                .findAllByBlockedAndActiveAndDeletedAndBusiness_Id
                        (false, true, false, id, pageable);

        Page<UserOfferResponse> response = offers.map(userOfferResponseMapper);
        String message = "Offers retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getOfferById(Long id) {
        Offer Offer = DatabaseService.get(OfferRepository::findActive, id, Offer.class);

        UserOfferResponse response = userOfferResponseMapper.apply(Offer);
        String message = "Offer retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public byte[] getOfferPosters(Long id) {
        Offer Offer = DatabaseService.get(OfferRepository::findActive, id, Offer.class);

        try {
            String filename = Offer.getPoster();

            Path filepath = PathConstants.OFFER_POSTER.resolve(filename);

            return Files.readAllBytes(filepath);

        } catch (IOException exception) {
            throw new RuntimeException("Failed load Offer poster.", exception);
        }
    }
}
