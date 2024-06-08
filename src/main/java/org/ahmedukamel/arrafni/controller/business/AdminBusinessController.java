package org.ahmedukamel.arrafni.controller.business;

import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.service.business.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AdminAuthorization
@RequestMapping(value = "api/v1/business")
public class AdminBusinessController {
    final IAdminBusinessService service;

    public AdminBusinessController(AdminBusinessService service) {
        this.service = service;
    }

    @PutMapping(value = "{businessId}/lock")
    public ResponseEntity<?> setLockStatus(
            @Min(value = 1) @PathVariable(value = "businessId") Long id,
            @RequestParam(value = "locked") boolean locked) {

        service.setLockStatus(id, locked);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "{businessId}/activate")
    public ResponseEntity<?> activateBusiness(@Min(value = 1) @PathVariable(value = "businessId") Long id) {
        service.activateBusiness(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "{businessId}/update")
    public ResponseEntity<?> approveBusinessUpdate(@Min(value = 1) @PathVariable(value = "businessId") Long id) {
        service.approveBusinessUpdate(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "licence/{licenceId}/activate")
    public ResponseEntity<?> activateBusinessLicence(@Min(value = 1) @PathVariable(value = "licenceId") Long id) {
        service.activateBusinessLicence(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "pending-activation-businesses")
    public ResponseEntity<?> getPendingActivationBusinesses(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "page", defaultValue = "1") int pageNumber
    ) {

        return ResponseEntity.ok().body(service.getPendingActivationBusinesses(pageSize, pageNumber));
    }

    @GetMapping(value = "pending-activation-licences")
    public ResponseEntity<?> getPendingActivationBusinessLicences(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "page", defaultValue = "1") int pageNumber
    ) {

        return ResponseEntity.ok().body(service.getPendingActivationBusinessLicences(pageSize, pageNumber));
    }

    @GetMapping(value = "pending-update-businesses")
    public ResponseEntity<?> getPendingUpdateApprovalBusinesses(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "page", defaultValue = "1") int pageNumber
    ) {

        return ResponseEntity.ok().body(service.getPendingUpdateApprovalBusinesses(pageSize, pageNumber));
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getAllBusinesses(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "page", defaultValue = "1") int pageNumber
    ) {

        return ResponseEntity.ok().body(service.getAllBusinesses(pageSize, pageNumber));
    }

    @GetMapping(value = "by-lock")
    public ResponseEntity<?> getBusinessesByLockStatus(
            @RequestParam(value = "locked") Boolean locked,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "page", defaultValue = "1") int pageNumber
    ) {

        return ResponseEntity.ok().body(service.getBusinessesByLockStatus(locked, pageSize, pageNumber));
    }
}