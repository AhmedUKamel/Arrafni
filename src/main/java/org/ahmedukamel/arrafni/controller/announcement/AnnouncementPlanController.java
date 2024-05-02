package org.ahmedukamel.arrafni.controller.announcement;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.dto.plan.CreatePlanRequest;
import org.ahmedukamel.arrafni.service.accouncement.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/announcement-plan")
public class AnnouncementPlanController {
    final IAnnouncementPlanService service;

    public AnnouncementPlanController(AnnouncementPlanService service) {
        this.service = service;
    }

    @AdminAuthorization
    @PostMapping
    public ResponseEntity<?> createAnnouncementPlan(@Valid @RequestBody CreatePlanRequest request) {
        return ResponseEntity.created(URI.create("api/v1/Announcement/plan")).body(service.createAnnouncementPlan(request));
    }

    @AdminAuthorization
    @PutMapping(value = "{AnnouncementPlanId}/activate")
    public ResponseEntity<?> setActiveStatus(@Min(value = 1) @PathVariable(value = "AnnouncementPlanId") Integer id,
                                             @RequestParam(value = "active") boolean active) {
        service.setActiveStatus(id, active);
        return ResponseEntity.noContent().build();
    }

    @AdminAuthorization
    @GetMapping(value = "{AnnouncementPlanId}")
    public ResponseEntity<?> readAnnouncementPlan(@Min(value = 1) @PathVariable(value = "AnnouncementPlanId") Integer id) {
        return ResponseEntity.ok().body(service.readAnnouncementPlan(id));
    }

    @AdminAuthorization
    @GetMapping
    public ResponseEntity<?> readAnnouncementPlans(@Min(value = 1) @RequestParam(value = "size", defaultValue = "10") long pageSize,
                                                   @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") long pageNumber) {
        return ResponseEntity.ok().body(service.readAnnouncementPlans(pageSize, pageNumber));
    }

    @GetMapping(value = "public/{AnnouncementPlanId}")
    public ResponseEntity<?> getAnnouncementPlan(@Min(value = 1) @PathVariable(value = "AnnouncementPlanId") Integer id) {
        return ResponseEntity.ok().body(service.getAnnouncementPlan(id));
    }

    @GetMapping(value = "public")
    public ResponseEntity<?> getAnnouncementPlans(@Min(value = 1) @RequestParam(value = "size", defaultValue = "10") long pageSize,
                                                  @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") long pageNumber) {
        return ResponseEntity.ok().body(service.getAnnouncementPlans(pageSize, pageNumber));
    }
}