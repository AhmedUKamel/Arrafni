package org.ahmedukamel.arrafni.controller.business;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.dto.business.CreateBusinessPlanRequest;
import org.ahmedukamel.arrafni.service.business.BusinessPlanService;
import org.ahmedukamel.arrafni.service.business.IBusinessPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/business-plan")
public class BusinessPlanController {
    final IBusinessPlanService service;

    public BusinessPlanController(BusinessPlanService service) {
        this.service = service;
    }

    @AdminAuthorization
    @PostMapping
    public ResponseEntity<?> createBusinessPlan(@Valid @RequestBody CreateBusinessPlanRequest request) {
        return ResponseEntity.created(URI.create("api/v1/business/plan")).body(service.createBusinessPlan(request));
    }

    @AdminAuthorization
    @PutMapping(value = "{businessPlanId}/activate")
    public ResponseEntity<?> setActiveStatus(@Min(value = 1) @PathVariable(value = "businessPlanId") Integer id,
                                             @RequestParam(value = "active") boolean active) {
        service.setActiveStatus(id, active);
        return ResponseEntity.noContent().build();
    }

    @AdminAuthorization
    @GetMapping(value = "{businessPlanId}")
    public ResponseEntity<?> readBusinessPlan(@Min(value = 1) @PathVariable(value = "businessPlanId") Integer id) {
        return ResponseEntity.ok().body(service.readBusinessPlan(id));
    }

    @AdminAuthorization
    @GetMapping
    public ResponseEntity<?> readBusinessPlans(@Min(value = 1) @RequestParam(value = "size", defaultValue = "10") long pageSize,
                                               @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") long pageNumber) {
        return ResponseEntity.ok().body(service.readBusinessPlans(pageSize, pageNumber));
    }

    @GetMapping(value = "public/{businessPlanId}")
    public ResponseEntity<?> getBusinessPlan(@Min(value = 1) @PathVariable(value = "businessPlanId") Integer id) {
        return ResponseEntity.ok().body(service.getBusinessPlan(id));
    }

    @GetMapping(value = "public")
    public ResponseEntity<?> getBusinessPlans(@Min(value = 1) @RequestParam(value = "size", defaultValue = "10") long pageSize,
                                              @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") long pageNumber) {
        return ResponseEntity.ok().body(service.getBusinessPlans(pageSize, pageNumber));
    }
}