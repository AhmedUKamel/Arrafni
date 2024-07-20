package org.ahmedukamel.arrafni.controller.business;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.dto.business.CreateBusinessNotificationPlanRequest;
import org.ahmedukamel.arrafni.service.business.BusinessNotificationAdminService;
import org.ahmedukamel.arrafni.service.business.IBusinessNotificationAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AdminAuthorization
@RequestMapping(value = "api/v1/b-notification")
public class BusinessNotificationAdminController {
    private final IBusinessNotificationAdminService service;

    public BusinessNotificationAdminController(BusinessNotificationAdminService service) {
        this.service = service;
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getPendingActivationBusinessNotificationLicences(
            @Min(value = 1) @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize) {

        return ResponseEntity.ok().body(service.getPendingActivationBusinessNotificationLicences(pageNumber, pageSize));
    }

    @PutMapping(value = "{licenceId}/activate")
    public ResponseEntity<?> activateBusinessNotificationLicences(
            @Min(value = 1) @PathVariable(value = "licenceId") Long id) {

        return ResponseEntity.ok().body(service.activateBusinessNotificationLicences(id));
    }

    @DeleteMapping(value = "{licenceId}")
    public ResponseEntity<?> deleteBusinessNotificationLicences(
            @Min(value = 1) @PathVariable(value = "licenceId") Long id) {

        return ResponseEntity.ok().body(service.deleteBusinessNotificationLicences(id));
    }

    @PostMapping(value = "plan")
    public ResponseEntity<?> createBusinessNotificationPlan(
            @Valid @RequestBody CreateBusinessNotificationPlanRequest request) {

        return ResponseEntity.ok().body(service.createBusinessNotificationPlan(request));
    }

    @PutMapping(value = "plan/{planId}/activate")
    public ResponseEntity<?> setBusinessNotificationPlanActiveStatus(
            @Min(value = 1) @PathVariable(value = "planId") Integer id,
            @RequestParam(value = "active") Boolean active) {

        return ResponseEntity.ok().body(service.setBusinessNotificationPlanActiveStatus(id, active));
    }

    @GetMapping(value = "admin/plan/all")
    public ResponseEntity<?> getBusinessNotificationPlans(
            @Min(value = 1) @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "active", required = false) Boolean active) {

        return ResponseEntity.ok().body(service.getBusinessNotificationPlans(pageNumber, pageSize, active));
    }
}
