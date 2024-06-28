package org.ahmedukamel.arrafni.saver.offer;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.constant.PathConstants;
import org.ahmedukamel.arrafni.dto.offer.CreateOfferRequest;
import org.ahmedukamel.arrafni.model.Offer;
import org.ahmedukamel.arrafni.model.OfferLicence;
import org.ahmedukamel.arrafni.model.OfferPlan;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.repository.OfferPlanRepository;
import org.ahmedukamel.arrafni.repository.OfferRepository;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.saver.FileSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class OfferSaver implements BiFunction<CreateOfferRequest, MultipartFile, Offer> {
    private final OfferPlanRepository OfferPlanRepository;
    private final OfferRepository OfferRepository;
    private final BusinessRepository businessRepository;
    private final FileSaver fileSaver;

    @Override
    public Offer apply(CreateOfferRequest request, MultipartFile poster) {
        Business business = DatabaseService.get(businessRepository::findById, request.businessId(), Business.class);

        if (!business.getOwner().getId().equals(ContextHolderUtils.getUser().getId())) {
            throw new IllegalStateException("You are not owner of this business");
        }

        Optional<String> optionalPosterId = fileSaver.apply(poster, PathConstants.OFFER_POSTER);

        if (optionalPosterId.isEmpty()) {
            throw new IllegalStateException("Could not save Offer poster");
        }

        Offer Offer = new Offer();
        Offer.setBusiness(business);
        Offer.setTitle(request.title().strip());
        Offer.setDescription(request.description().strip());
        Offer.setPoster(optionalPosterId.get());

        OfferPlan plan = DatabaseService.get(OfferPlanRepository::findById, request.offerPlanId(), OfferPlan.class);

        OfferLicence licence = new OfferLicence();
        licence.setOffer(Offer);
        licence.setExpiration(LocalDateTime.now().plusDays(plan.getDays()));
        licence.setPlan(plan);

        Offer.setLicences(Set.of(licence));

        return OfferRepository.save(Offer);
    }
}