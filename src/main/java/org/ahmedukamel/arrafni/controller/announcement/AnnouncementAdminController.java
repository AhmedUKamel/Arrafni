package org.ahmedukamel.arrafni.controller.announcement;

import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.service.accouncement.AnnouncementAdminService;
import org.ahmedukamel.arrafni.service.accouncement.IAnnouncementAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/announcement")
@AdminAuthorization
public class AnnouncementAdminController {
    private final IAnnouncementAdminService service;

    public AnnouncementAdminController(AnnouncementAdminService service) {
        this.service = service;
    }

    @GetMapping(value = "licences/pending-activation")
    public ResponseEntity<?> getPendingAnnouncementLicences(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getPendingAnnouncementLicences(pageSize, pageNumber));
    }

    @PutMapping(value = "licence/{licenceId}/activate")
    public ResponseEntity<?> activateAnnouncementLicence(
            @Min(value = 1) @PathVariable(value = "licenceId") Long id) {

        return ResponseEntity.accepted().body(service.activateAnnouncementLicence(id));
    }

    @PutMapping(value = "{announcementId}/block")
    public ResponseEntity<?> blockAnnouncement(
            @Min(value = 1) @PathVariable(value = "announcementId") Long id,
            @RequestParam(value = "blocked") Boolean blocked) {

        return ResponseEntity.accepted().body(service.setAnnouncementBlockStatus(id, blocked));
    }
}