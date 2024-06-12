package org.ahmedukamel.arrafni.controller.announcement;

import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.service.accouncement.AnnouncementUserService;
import org.ahmedukamel.arrafni.service.accouncement.IAnnouncementUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/announcement")
public class AnnouncementUserController {
    private final IAnnouncementUserService service;

    public AnnouncementUserController(AnnouncementUserService service) {
        this.service = service;
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getAllAnnouncements(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getAllAnnouncements(pageSize, pageNumber));
    }

    @GetMapping(value = "premium")
    public ResponseEntity<?> getPremiumAnnouncements(
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getPremiumAnnouncements(pageSize, pageNumber));
    }

    @GetMapping(value = "by-sub-category")
    public ResponseEntity<?> getBusinessAnnouncements(
            @Min(value = 1) @RequestParam(value = "subCategoryId") Integer subCategoryId,
            @Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok().body(service.getSubCategoryAnnouncements(subCategoryId, pageSize, pageNumber));
    }
}