package org.ahmedukamel.arrafni.controller.announcement;

import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.service.accouncement.AnnouncementPublicService;
import org.ahmedukamel.arrafni.service.accouncement.IAnnouncementPublicService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/announcement/public")
public class AnnouncementPublicController {
    private final IAnnouncementPublicService service;

    public AnnouncementPublicController(AnnouncementPublicService service) {
        this.service = service;
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getAllAnnouncements(
            @Min(value = 1) @RequestParam(value = "regionId") Integer id,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getAllAnnouncements(id, pageSize, pageNumber));
    }

    @GetMapping(value = "premium")
    public ResponseEntity<?> getPremiumAnnouncements(
            @Min(value = 1) @RequestParam(value = "regionId") Integer id,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getPremiumAnnouncements(id, pageSize, pageNumber));
    }

    @GetMapping(value = "by-business")
    public ResponseEntity<?> getBusinessAnnouncements(
            @Min(value = 1) @RequestParam(value = "businessId") Long id,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getBusinessAnnouncements(id, pageSize, pageNumber));
    }

    @GetMapping(value = "by-sub-category")
    public ResponseEntity<?> getSubCategoryAnnouncements(
            @Min(value = 1) @RequestParam(value = "regionId") Integer regionId,
            @Min(value = 1) @RequestParam(value = "subCategoryId") Integer subCategoryId,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getSubCategoryAnnouncements(regionId, subCategoryId, pageSize, pageNumber));
    }

    @GetMapping(value = "by-main-category")
    public ResponseEntity<?> getMainCategoryAnnouncements(
            @Min(value = 1) @RequestParam(value = "regionId") Integer regionId,
            @Min(value = 1) @RequestParam(value = "mainCategoryId") Integer mainCategoryId,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getMainCategoryAnnouncements(regionId, mainCategoryId, pageSize, pageNumber));
    }

    @GetMapping(value = "{announcementId}")
    public ResponseEntity<?> getAnnouncementById(
            @Min(value = 1) @PathVariable(value = "announcementId") Long id) {

        return ResponseEntity.ok().body(service.getAnnouncementById(id));
    }

    @GetMapping(value = "{announcementId}/poster")
    public ResponseEntity<?> getAnnouncementPosters(
            @Min(value = 1) @PathVariable(value = "announcementId") Long id) {

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(service.getAnnouncementPosters(id));
    }
}