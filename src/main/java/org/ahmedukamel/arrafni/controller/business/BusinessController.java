package org.ahmedukamel.arrafni.controller.business;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.ahmedukamel.arrafni.service.business.IBusinessService;
import org.ahmedukamel.arrafni.service.business.BusinessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/business")
public class BusinessController {
    private final IBusinessService service;

    public BusinessController(BusinessService service) {
        this.service = service;
    }

    @GetMapping(value = "public/location/search")
    public ResponseEntity<?> searchBusinessesByLocation(
            @NotBlank @RequestParam(value = "word") String word,
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "longitude") Double longitude,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") long pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") long pageNumber
    ) {
        return ResponseEntity.ok().body(service.searchBusinessesByLocation(word, latitude, longitude, pageSize, pageNumber));
    }

    @GetMapping(value = "public/location/sub-category")
    public ResponseEntity<?> readBusinessesBySubCategoryByLocation(
            @Min(value = 1) @RequestParam(value = "id") Integer subCategoryId,
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "longitude") Double longitude,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") long pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") long pageNumber
    ) {
        return ResponseEntity.ok().body(service.readBusinessesBySubCategoryByLocation(subCategoryId, latitude, longitude, pageSize, pageNumber));
    }

    @GetMapping(value = "public/location/main-category")
    public ResponseEntity<?> readBusinessesByMainCategoryByLocation(
            @Min(value = 1) @RequestParam(value = "id") Integer subCategoryId,
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "longitude") Double longitude,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") long pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") long pageNumber
    ) {
        return ResponseEntity.ok().body(service.readBusinessesByMainCategoryByLocation(subCategoryId, latitude, longitude, pageSize, pageNumber));
    }

    @GetMapping(value = "public/region/search")
    public ResponseEntity<?> searchBusinessesByRegionId(
            @NotBlank @RequestParam(value = "word") String word,
            @Min(value = 1) @RequestParam(value = "regionId") Integer regionId,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") long pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") long pageNumber
    ) {
        return ResponseEntity.ok().body(service.searchBusinessesByRegionId(word, regionId, pageSize, pageNumber));
    }

    @GetMapping(value = "public/region/sub-category")
    public ResponseEntity<?> readBusinessesBySubCategoryByRegionId(
            @Min(value = 1) @RequestParam(value = "id") Integer subCategoryId,
            @Min(value = 1) @RequestParam(value = "regionId") Integer regionId,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") long pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") long pageNumber
    ) {
        return ResponseEntity.ok().body(service.readBusinessesBySubCategoryByRegionId(subCategoryId, regionId, pageSize, pageNumber));
    }

    @GetMapping(value = "public/region/main-category")
    public ResponseEntity<?> readBusinessesByMainCategoryByRegionId(
            @Min(value = 1) @RequestParam(value = "id") Integer subCategoryId,
            @Min(value = 1) @RequestParam(value = "regionId") Integer regionId,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") long pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") long pageNumber
    ) {
        return ResponseEntity.ok().body(service.readBusinessesByMainCategoryByRegionId(subCategoryId, regionId, pageSize, pageNumber));
    }

    @GetMapping(value = "public/{businessId}")
    public ResponseEntity<?> readBusiness(@Min(value = 1) @PathVariable(value = "businessId") Long id) {
        return ResponseEntity.ok().body(service.readBusiness(id));
    }

    @PutMapping(value = "{businessId}/favorite")
    public ResponseEntity<?> setFavoriteBusiness(@Min(value = 1) @PathVariable(value = "businessId") Long id,
                                                 @RequestParam(value = "isFavorite") Boolean isFavorite) {
        service.setFavoriteBusiness(id, isFavorite);
        return ResponseEntity.noContent().build();
    }
}