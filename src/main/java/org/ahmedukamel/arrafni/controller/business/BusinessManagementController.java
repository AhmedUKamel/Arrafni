package org.ahmedukamel.arrafni.controller.business;

import jakarta.validation.Valid;
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
    public ResponseEntity<?> createBusiness(@Valid @RequestParam(value = "business") CreateBusinessRequest request,
                                            @NotEmpty @RequestParam(value = "logo") MultipartFile logoFile,
                                            @NotEmpty @Size(min = 1, max = 5) @RequestParam(value = "pictures") MultipartFile[] picturesFiles) {
        return ResponseEntity.created(URI.create("/api/v1/business")).body(service.createBusiness(request, logoFile, picturesFiles));
    }

    @GetMapping(value = "{businessId}")
    public ResponseEntity<?> readBusiness(@PathVariable(value = "businessId") Long id) {
        return ResponseEntity.ok().body(service.readBusiness(id));
    }

    // TODO: Implement Update Business Request
    @PutMapping(value = "{businessId}")
    public ResponseEntity<?> updateBusiness(@PathVariable(value = "businessId") Long id) {
        return ResponseEntity.ok().body(service.updateBusiness(id, ""));
    }

    @DeleteMapping(value = "{businessId}")
    public ResponseEntity<?> deleteBusiness(@PathVariable(value = "businessId") Long id) {
        service.deleteBusiness(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "{businessId}/logo")
    public ResponseEntity<?> uploadLogo(@PathVariable(value = "businessId") Long id,
                                        @NotEmpty MultipartFile file) {
        service.uploadLogo(id, file);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "{businessId}/pictures")
    public ResponseEntity<?> uploadLogo(@PathVariable(value = "businessId") Long id,
                                        @NotEmpty MultipartFile[] files) {
        service.uploadImages(id, files);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "{businessId}/pictures")
    public ResponseEntity<?> deleteImages(@PathVariable(value = "businessId") Long id,
                                          @RequestParam(value = "pictures") List<String> pictures) {
        service.deleteImages(id, pictures);
        return ResponseEntity.noContent().build();
    }
}