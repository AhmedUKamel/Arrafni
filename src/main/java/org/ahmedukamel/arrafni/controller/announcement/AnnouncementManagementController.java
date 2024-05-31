package org.ahmedukamel.arrafni.controller.announcement;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.annotation.NotEmpty;
import org.ahmedukamel.arrafni.dto.announcement.CreateAnnouncementRequest;
import org.ahmedukamel.arrafni.dto.announcement.RenewAnnouncementRequest;
import org.ahmedukamel.arrafni.service.accouncement.AnnouncementManagementService;
import org.ahmedukamel.arrafni.service.accouncement.IAnnouncementManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Validated
@RestController
@RequestMapping(value = "api/v1/my-announcement")
public class AnnouncementManagementController {
    private final IAnnouncementManagementService service;

    public AnnouncementManagementController(AnnouncementManagementService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createAnnouncement(
            @Valid @RequestParam(value = "announcement") CreateAnnouncementRequest request,
            @NotEmpty @RequestParam(value = "poster") MultipartFile poster) {

        return ResponseEntity.created(URI.create("api/v1/my-announcement"))
                .body(service.createAnnouncement(request, poster));
    }

    @DeleteMapping(value = "{announcementId}")
    public ResponseEntity<?> deleteAnnouncement(@Min(value = 1) @PathVariable(value = "announcementId") Long id) {
        service.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "renew")
    public ResponseEntity<?> renewAnnouncement(@Valid @RequestBody RenewAnnouncementRequest request) {
        return ResponseEntity.accepted().body(service.renewAnnouncement(request));
    }

    @GetMapping(value = "{announcementId}/licences")
    public ResponseEntity<?> getLicences(@Min(value = 1) @PathVariable(value = "announcementId") Long id) {
        return ResponseEntity.ok().body(service.getAnnouncementLicences(id));
    }

    @GetMapping(value = "{announcementId}")
    public ResponseEntity<?> getAnnouncement(@Min(value = 1) @PathVariable(value = "announcementId") Long id) {
        return ResponseEntity.ok().body(service.getAnnouncement(id));
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getAnnouncements(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getAnnouncements(pageSize, pageNumber));
    }

    @GetMapping(value = "by-business")
    public ResponseEntity<?> getBusinessAnnouncements(
            @Min(value = 1) @RequestParam(value = "businessId") Long id,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getBusinessAnnouncements(id, pageSize, pageNumber));
    }
}