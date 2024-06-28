package org.ahmedukamel.arrafni.controller.offer;

import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.service.offer.IOfferPublicService;
import org.ahmedukamel.arrafni.service.offer.OfferPublicService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/offer/public")
public class OfferPublicController {
    private final IOfferPublicService service;

    public OfferPublicController(OfferPublicService service) {
        this.service = service;
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getAllOffers(
            @Min(value = 1) @RequestParam(value = "regionId") Integer id,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getAllOffers(id, pageSize, pageNumber));
    }

//    @GetMapping(value = "premium")
//    public ResponseEntity<?> getPremiumOffers(
//            @Min(value = 1) @RequestParam(value = "regionId") Integer id,
//            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
//            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {
//
//        return ResponseEntity.ok().body(service.getPremiumOffers(id, pageSize, pageNumber));
//    }

    @GetMapping(value = "by-business")
    public ResponseEntity<?> getBusinessOffers(
            @Min(value = 1) @RequestParam(value = "businessId") Long id,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getBusinessOffers(id, pageSize, pageNumber));
    }

    @GetMapping(value = "by-sub-category")
    public ResponseEntity<?> getSubCategoryOffers(
            @Min(value = 1) @RequestParam(value = "regionId") Integer regionId,
            @Min(value = 1) @RequestParam(value = "subCategoryId") Integer subCategoryId,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getSubCategoryOffers(regionId, subCategoryId, pageSize, pageNumber));
    }

    @GetMapping(value = "by-main-category")
    public ResponseEntity<?> getMainCategoryOffers(
            @Min(value = 1) @RequestParam(value = "regionId") Integer regionId,
            @Min(value = 1) @RequestParam(value = "mainCategoryId") Integer mainCategoryId,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getMainCategoryOffers(regionId, mainCategoryId, pageSize, pageNumber));
    }

    @GetMapping(value = "{offerId}")
    public ResponseEntity<?> getOfferById(
            @Min(value = 1) @PathVariable(value = "offerId") Long id) {

        return ResponseEntity.ok().body(service.getOfferById(id));
    }

    @GetMapping(value = "{offerId}/poster")
    public ResponseEntity<?> getOfferPosters(
            @Min(value = 1) @PathVariable(value = "offerId") Long id) {

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(service.getOfferPosters(id));
    }
}