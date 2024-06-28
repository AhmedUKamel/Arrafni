package org.ahmedukamel.arrafni.controller.offer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.annotation.NotEmpty;
import org.ahmedukamel.arrafni.dto.offer.CreateOfferRequest;
import org.ahmedukamel.arrafni.dto.offer.RenewOfferRequest;
import org.ahmedukamel.arrafni.service.offer.OfferManagementService;
import org.ahmedukamel.arrafni.service.offer.IOfferManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Validated
@RestController
@RequestMapping(value = "api/v1/my-offer")
public class OfferManagementController {
    private final IOfferManagementService service;

    public OfferManagementController(OfferManagementService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createOffer(
            @Valid @RequestParam(value = "Offer") CreateOfferRequest request,
            @NotEmpty @RequestParam(value = "poster") MultipartFile poster) {

        return ResponseEntity.created(URI.create("api/v1/my-offer"))
                .body(service.createOffer(request, poster));
    }

    @DeleteMapping(value = "{offerId}")
    public ResponseEntity<?> deleteOffer(@Min(value = 1) @PathVariable(value = "offerId") Long id) {
        service.deleteOffer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "renew")
    public ResponseEntity<?> renewOffer(@Valid @RequestBody RenewOfferRequest request) {
        return ResponseEntity.accepted().body(service.renewOffer(request));
    }

    @GetMapping(value = "{offerId}/licences")
    public ResponseEntity<?> getLicences(@Min(value = 1) @PathVariable(value = "offerId") Long id) {
        return ResponseEntity.ok().body(service.getOfferLicences(id));
    }

    @GetMapping(value = "{offerId}")
    public ResponseEntity<?> getOffer(@Min(value = 1) @PathVariable(value = "offerId") Long id) {
        return ResponseEntity.ok().body(service.getOffer(id));
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getOffers(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getOffers(pageSize, pageNumber));
    }

    @GetMapping(value = "by-business")
    public ResponseEntity<?> getBusinessOffers(
            @Min(value = 1) @RequestParam(value = "businessId") Long id,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getBusinessOffers(id, pageSize, pageNumber));
    }
}