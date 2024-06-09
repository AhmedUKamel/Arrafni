package org.ahmedukamel.arrafni.controller.business;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.ahmedukamel.arrafni.annotation.NotEmpty;
import org.ahmedukamel.arrafni.dto.business.CreateBusinessRequest;
import org.ahmedukamel.arrafni.service.business.BusinessManagementService;
import org.ahmedukamel.arrafni.service.business.IBusinessManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping(value = "api/v1/business")
public class BusinessManagementController {
    private final IBusinessManagementService service;

    public BusinessManagementController(BusinessManagementService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createBusiness(
            @Valid @RequestParam(value = "business") CreateBusinessRequest request,
            @NotEmpty @RequestParam(value = "logo") MultipartFile logoFile,
            @NotEmpty @Size(min = 1, max = 5) @RequestParam(value = "pictures") MultipartFile[] picturesFiles) {

        return ResponseEntity.created(URI.create("/api/v1/business")).body(service.createBusiness(request, logoFile, picturesFiles));
    }

    @GetMapping(value = "{businessId}")
    public ResponseEntity<?> readBusiness(@Min(value = 1) @PathVariable(value = "businessId") Long id) {
        return ResponseEntity.ok().body(service.readBusiness(id));
    }

    // TODO: Implement Update Business Request
    @PutMapping(value = "{businessId}")
    public ResponseEntity<?> updateBusiness(@Min(value = 1) @PathVariable(value = "businessId") Long id) {
        return ResponseEntity.ok().body(service.updateBusiness(id, ""));
    }

    @PutMapping(value = "buy-licence")
    public ResponseEntity<?> buyBusinessLicence(
            @Min(value = 1) @RequestParam(value = "businessId") Long businessId,
            @Min(value = 1) @RequestParam(value = "planId") Integer planId) {
        return ResponseEntity.ok().body(service.buyBusinessLicence(businessId, planId));
    }

    @GetMapping(value = "my")
    public ResponseEntity<?> getMyBusinesses(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "page", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getMyBusinesses(pageSize, pageNumber));
    }

    @DeleteMapping(value = "{businessId}")
    public ResponseEntity<?> deleteBusiness(@Min(value = 1) @PathVariable(value = "businessId") Long id) {
        service.deleteBusiness(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "{businessId}/logo")
    public ResponseEntity<?> uploadLogo(
            @Min(value = 1) @PathVariable(value = "businessId") Long id,
            @NotEmpty @RequestParam(value = "logo") MultipartFile file) {

        service.uploadLogo(id, file);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "{businessId}/pictures")
    public ResponseEntity<?> uploadImages(
            @Min(value = 1) @PathVariable(value = "businessId") Long id,
            @NotEmpty @RequestParam(value = "pictures") MultipartFile[] files) {

        service.uploadImages(id, files);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "{businessId}/pictures")
    public ResponseEntity<?> deleteImages(
            @Min(value = 1) @PathVariable(value = "businessId") Long id,
            @RequestParam(value = "pictures") List<String> pictures) {

        service.deleteImages(id, pictures);
        return ResponseEntity.noContent().build();
    }
}