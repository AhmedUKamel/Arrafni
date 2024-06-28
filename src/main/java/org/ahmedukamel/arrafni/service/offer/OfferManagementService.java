package org.ahmedukamel.arrafni.service.offer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.offer.CreateOfferRequest;
import org.ahmedukamel.arrafni.dto.offer.OwnerOfferLicenceResponse;
import org.ahmedukamel.arrafni.dto.offer.OwnerOfferResponse;
import org.ahmedukamel.arrafni.dto.offer.RenewOfferRequest;
import org.ahmedukamel.arrafni.mapper.offer.OwnerOfferLicenceResponseMapper;
import org.ahmedukamel.arrafni.mapper.offer.OwnerOfferResponseMapper;
import org.ahmedukamel.arrafni.model.*;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.repository.OfferPlanRepository;
import org.ahmedukamel.arrafni.repository.OfferRepository;
import org.ahmedukamel.arrafni.saver.offer.OfferSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OfferManagementService implements IOfferManagementService {
    private final OwnerOfferLicenceResponseMapper ownerOfferLicenceResponseMapper;
    private final OwnerOfferResponseMapper ownerOfferResponseMapper;
    private final OfferPlanRepository OfferPlanRepository;
    private final OfferRepository OfferRepository;
    private final BusinessRepository businessRepository;
    private final OfferSaver OfferSaver;

    @Override
    public Object createOffer(Object object, MultipartFile poster) {
        CreateOfferRequest request = (CreateOfferRequest) object;

        Offer savedOffer = OfferSaver.apply(request, poster);

        OwnerOfferResponse response = ownerOfferResponseMapper.apply(savedOffer);
        String message = "Offer created successfully.";

        return new ApiResponse(true, message, response);
    }

    @Override
    public void deleteOffer(Long id) {
        Offer Offer = getUserOffer(id);

        Offer.setDeleted(true);
        OfferRepository.save(Offer);
    }

    @Transactional
    @Override
    public Object renewOffer(Object object) {
        RenewOfferRequest request = (RenewOfferRequest) object;

        Offer offer = getUserOffer(request.offerId());

        if (Objects.nonNull(offer.getExpiration()) && offer.getExpiration().isAfter(LocalDateTime.now())) {
            throw new IllegalStateException("Offer already active.");
        }

        OfferPlan plan = DatabaseService.get(OfferPlanRepository::findById,
                request.offerPlanId(), OfferPlan.class);

        OfferLicence licence = new OfferLicence();
        licence.setOffer(offer);
        licence.setPlan(plan);
        licence.setExpiration(LocalDateTime.now().plusDays(plan.getDays()));

        offer.getLicences().add(licence);
        OfferRepository.save(offer);

        String message = "Offer renewed successfully.";

        return new ApiResponse(true, message, "");
    }

    @Transactional
    @Override
    public Object getOfferLicences(Long id) {
        Offer offer = getUserOffer(id);

        Collection<OfferLicence> licences = offer.getLicences();

        Collection<OwnerOfferLicenceResponse> response = licences
                .stream()
                .map(ownerOfferLicenceResponseMapper)
                .toList();
        String message = "Offer licences retrieved successfully.";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getOffer(Long id) {
        Offer offer = getUserOffer(id);

        OwnerOfferResponse response = ownerOfferResponseMapper.apply(offer);
        String message = "Offer retrieved successfully.";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getOffers(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        User user = ContextHolderUtils.getUser();

        Page<Offer> offers = OfferRepository.findAllByBusiness_Owner_Id(user.getId(), pageable);

        Page<OwnerOfferResponse> response = offers.map(ownerOfferResponseMapper);
        String message = "Offer retrieved successfully.";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getBusinessOffers(Long id, int pageSize, int pageNumber) {
        Business business = getUserBusiness(id);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Offer> offers = OfferRepository.findAllByBusiness_Id(business.getId(), pageable);

        Page<OwnerOfferResponse> response = offers.map(ownerOfferResponseMapper);
        String message = "Offer retrieved successfully.";

        return new ApiResponse(true, message, response);
    }

    private Offer getUserOffer(Long id) {
        Offer offers = DatabaseService.get(OfferRepository::findById, id, Offer.class);

        if (!offers.getBusiness().getOwner().getId().equals(ContextHolderUtils.getUser().getId())) {
            throw new IllegalStateException("You are not owner of this Offer.");
        }

        return offers;
    }

    private Business getUserBusiness(Long id) {
        Business business = DatabaseService.get(businessRepository::findById, id, Business.class);

        if (!business.getOwner().getId().equals(ContextHolderUtils.getUser().getId())) {
            throw new IllegalStateException("You are not owner of this business.");
        }

        return business;
    }
}
