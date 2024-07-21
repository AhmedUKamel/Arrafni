package org.ahmedukamel.arrafni.controller.business;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.dto.business.AddBusinessNotificationRequest;
import org.ahmedukamel.arrafni.dto.business.SendBusinessNotificationRequest;
import org.ahmedukamel.arrafni.service.business.BusinessNotificationUserService;
import org.ahmedukamel.arrafni.service.business.IBusinessNotificationUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping(value = "api/v1/b-notification")
public class BusinessNotificationUserController {
    private final IBusinessNotificationUserService service;

    public BusinessNotificationUserController(BusinessNotificationUserService service) {
        this.service = service;
    }

    @PostMapping(value = "my/send")
    public ResponseEntity<?> sendBusinessNotification(
            @Valid @RequestParam(value = "request") SendBusinessNotificationRequest request,
            @RequestParam(value = "image", required = false) MultipartFile file) {

        return ResponseEntity.accepted().body(service.sendBusinessNotification(request, file));
    }

    @PostMapping(value = "my/add")
    public ResponseEntity<?> addBusinessNotification(
            @Valid @RequestBody AddBusinessNotificationRequest request) {

        return ResponseEntity.accepted().body(service.addBusinessNotification(request));
    }

    @GetMapping(value = "my/all")
    public ResponseEntity<?> getMyBusinessNotificationLicences(
            @Min(value = 1) @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize) {

        return ResponseEntity.ok().body(service.getMyBusinessNotificationLicences(pageNumber, pageSize));
    }

    @GetMapping(value = "plan/{planId}")
    public ResponseEntity<?> getBusinessNotificationPlan(
            @Min(value = 1) @PathVariable(value = "planId") Integer id) {

        return ResponseEntity.ok().body(service.getBusinessNotificationPlan(id));
    }

    @GetMapping(value = "plan/all")
    public ResponseEntity<?> getBusinessNotificationPlans(
            @Min(value = 1) @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize) {

        return ResponseEntity.ok().body(service.getBusinessNotificationPlans(pageNumber, pageSize));
    }
}
