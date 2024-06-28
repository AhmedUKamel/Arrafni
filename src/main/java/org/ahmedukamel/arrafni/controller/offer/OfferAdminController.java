package org.ahmedukamel.arrafni.controller.offer;

import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.service.offer.IOfferAdminService;
import org.ahmedukamel.arrafni.service.offer.OfferAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/offer")
@AdminAuthorization
public class OfferAdminController {
    private final IOfferAdminService service;

    public OfferAdminController(OfferAdminService service) {
        this.service = service;
    }

    @GetMapping(value = "licences/pending-activation")
    public ResponseEntity<?> getPendingOfferLicences(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getPendingOfferLicences(pageSize, pageNumber));
    }

    @PutMapping(value = "licence/{licenceId}/activate")
    public ResponseEntity<?> activateOfferLicence(
            @Min(value = 1) @PathVariable(value = "licenceId") Long id) {

        return ResponseEntity.accepted().body(service.activateOfferLicence(id));
    }

    @PutMapping(value = "{offerId}/block")
    public ResponseEntity<?> blockOffer(
            @Min(value = 1) @PathVariable(value = "offerId") Long id,
            @RequestParam(value = "blocked") Boolean blocked) {

        return ResponseEntity.accepted().body(service.setOfferBlockStatus(id, blocked));
    }
}