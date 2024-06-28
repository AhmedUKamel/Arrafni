package org.ahmedukamel.arrafni.service.offer;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.offer.AdminOfferLicenceResponse;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.mapper.offer.AdminOfferLicenceResponseMapper;
import org.ahmedukamel.arrafni.model.Offer;
import org.ahmedukamel.arrafni.model.OfferLicence;
import org.ahmedukamel.arrafni.model.OfferPlan;
import org.ahmedukamel.arrafni.repository.OfferLicenceRepository;
import org.ahmedukamel.arrafni.repository.OfferRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OfferAdminService implements IOfferAdminService {
    private final AdminOfferLicenceResponseMapper adminOfferLicenceResponseMapper;
    private final OfferLicenceRepository OfferLicenceRepository;
    private final OfferRepository OfferRepository;

    @Override
    public Object getPendingOfferLicences(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<OfferLicence> licences = OfferLicenceRepository.findAllByValid(false, pageable);

        Page<AdminOfferLicenceResponse> response = licences.map(adminOfferLicenceResponseMapper);
        String message = "Inactive Offer licences retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object activateOfferLicence(Long id) {
        OfferLicence licence = DatabaseService.get(OfferLicenceRepository::findById, id, OfferLicence.class);

        if (licence.isValid()) {
            throw new IllegalStateException("Licence already activated");
        }

        OfferPlan plan = licence.getPlan();
        Offer offer = licence.getOffer();

        offer.setExpiration(LocalDateTime.now().plusDays(plan.getDays()));
        offer.setActive(true);

        licence.setValid(true);
        licence.setManual(true);

        OfferRepository.save(offer);
        OfferLicenceRepository.save(licence);

        String message = "Licence activated successfully";

        return new ApiResponse(true, message, "");
    }

    @Override
    public Object setOfferBlockStatus(Long id, Boolean blocked) {
        Offer offer = DatabaseService.get(OfferRepository::findById, id, Offer.class);

        offer.setBlocked(blocked);

        OfferRepository.save(offer);

        String message = "Offer block status set successfully";

        return new ApiResponse(true, message, "");
    }
}