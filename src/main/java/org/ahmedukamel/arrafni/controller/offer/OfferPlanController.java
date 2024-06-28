package org.ahmedukamel.arrafni.controller.offer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.dto.offer.CreateOfferPlanRequest;
import org.ahmedukamel.arrafni.service.offer.OfferPlanService;
import org.ahmedukamel.arrafni.service.offer.IOfferPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/offer-plan")
public class OfferPlanController {
    final IOfferPlanService service;

    public OfferPlanController(OfferPlanService service) {
        this.service = service;
    }

    @AdminAuthorization
    @PostMapping
    public ResponseEntity<?> createOfferPlan(
            @Valid @RequestBody CreateOfferPlanRequest request) {

        return ResponseEntity.created(URI.create("api/v1/offer-plan"))
                .body(service.createOfferPlan(request));
    }

    @AdminAuthorization
    @PutMapping(value = "{offerPlanId}/activate")
    public ResponseEntity<?> setActiveStatus(
            @Min(value = 1) @PathVariable(value = "offerPlanId") Integer id,
            @RequestParam(value = "active") boolean active) {

        service.setActiveStatus(id, active);
        return ResponseEntity.noContent().build();
    }

    @AdminAuthorization
    @GetMapping(value = "{offerPlanId}")
    public ResponseEntity<?> readOfferPlan(
            @Min(value = 1) @PathVariable(value = "offerPlanId") Integer id) {

        return ResponseEntity.ok().body(service.readOfferPlan(id));
    }

    @AdminAuthorization
    @GetMapping
    public ResponseEntity<?> readOfferPlans(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.readOfferPlans(pageSize, pageNumber));
    }

    @GetMapping(value = "public/{offerPlanId}")
    public ResponseEntity<?> getOfferPlan(
            @Min(value = 1) @PathVariable(value = "offerPlanId") Integer id) {

        return ResponseEntity.ok().body(service.getOfferPlan(id));
    }

    @GetMapping(value = "public")
    public ResponseEntity<?> getOfferPlans(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getOfferPlans(pageSize, pageNumber));
    }
}