package org.ahmedukamel.arrafni.controller.offer;

import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.service.offer.OfferUserService;
import org.ahmedukamel.arrafni.service.offer.IOfferUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/offer")
public class OfferUserController {
    private final IOfferUserService service;

    public OfferUserController(OfferUserService service) {
        this.service = service;
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getAllOffers(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getAllOffers(pageSize, pageNumber));
    }

//    @GetMapping(value = "premium")
//    public ResponseEntity<?> getPremiumOffers(
//            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
//            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {
//
//        return ResponseEntity.ok().body(service.getPremiumOffers(pageSize, pageNumber));
//    }

    @GetMapping(value = "by-sub-category")
    public ResponseEntity<?> getBusinessOffers(
            @Min(value = 1) @RequestParam(value = "subCategoryId") Integer subCategoryId,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getSubCategoryOffers(subCategoryId, pageSize, pageNumber));
    }

    @GetMapping(value = "by-main-category")
    public ResponseEntity<?> getMainCategoryOffers(
            @Min(value = 1) @RequestParam(value = "mainCategoryId") Integer mainCategoryId,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getMainCategoryOffers(mainCategoryId, pageSize, pageNumber));
    }
}