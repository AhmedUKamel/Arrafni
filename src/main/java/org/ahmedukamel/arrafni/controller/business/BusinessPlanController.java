package org.ahmedukamel.arrafni.controller.business;

import jakarta.validation.Valid;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.dto.business.CreateBusinessPlanRequest;
import org.ahmedukamel.arrafni.service.business.IBusinessPlanService;
import org.ahmedukamel.arrafni.service.business.BusinessPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/business/plan")
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
    @PutMapping(value = "active/{businessPlanId}")
    public ResponseEntity<?> setActiveStatus(@PathVariable(value = "businessPlanId") Integer id,
                                             @RequestParam(value = "active") boolean active) {
        service.setActiveStatus(id, active);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "public/{businessPlanId}")
    public ResponseEntity<?> readBusinessPlan(@PathVariable(value = "businessPlanId") Integer id) {
        return ResponseEntity.ok().body(service.readBusinessPlan(id));
    }

    @GetMapping(value = "public")
    public ResponseEntity<?> readCategories(@RequestParam(value = "size", required = false) Optional<Integer> pageSize,
                                            @RequestParam(value = "number", required = false) Optional<Integer> pageNumber) {
        return ResponseEntity.ok().body(
                (pageSize.isPresent() && pageNumber.isPresent()) ?
                        service.readBusinessPlans(pageSize.get(), pageNumber.get()) :
                        service.readBusinessPlans()
        );
    }
}