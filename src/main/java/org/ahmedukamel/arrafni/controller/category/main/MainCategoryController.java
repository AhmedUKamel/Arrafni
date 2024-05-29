package org.ahmedukamel.arrafni.controller.category.main;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.annotation.NotEmpty;
import org.ahmedukamel.arrafni.service.category.main.IMainCategoryService;
import org.ahmedukamel.arrafni.service.category.main.MainCategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/main-category")
public class MainCategoryController {
    private final IMainCategoryService service;

    public MainCategoryController(MainCategoryService service) {
        this.service = service;
    }

    @AdminAuthorization
    @PostMapping
    public ResponseEntity<?> createMainCategory(@NotBlank @RequestParam(value = "name") String name, @NotEmpty @RequestParam(value = "logo") MultipartFile logo) {
        return ResponseEntity.created(URI.create("api/v1/main-category")).body(service.createMainCategory(name, logo));
    }

    @AdminAuthorization
    @PutMapping(value = "{mainCategoryId}")
    public ResponseEntity<?> updateMainCategory(@Min(value = 1) @PathVariable(value = "mainCategoryId") Integer id, @NotBlank @RequestParam(value = "name") String name) {
        return ResponseEntity.ok().body(service.updateMainCategory(id, name));
    }

    @AdminAuthorization
    @PutMapping(value = "{mainCategoryId}/logo")
    public ResponseEntity<?> uploadMainCategoryLogo(@Min(value = 1) @PathVariable(value = "mainCategoryId") Integer id, @NotEmpty @RequestParam(value = "logo") MultipartFile logo) {
        return ResponseEntity.ok().body(service.uploadMainCategoryLogo(id, logo));
    }

    @AdminAuthorization
    @DeleteMapping(value = "{mainCategoryId}")
    public ResponseEntity<?> deleteMainCategory(@Min(value = 1) @PathVariable(value = "mainCategoryId") Integer id) {
        service.deleteMainCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "public/{mainCategoryId}")
    public ResponseEntity<?> getMainCategory(@Min(value = 1) @PathVariable(value = "mainCategoryId") Integer id) {
        return ResponseEntity.ok().body(service.getMainCategory(id));
    }

    @GetMapping(value = "public")
    public ResponseEntity<?> getMainCategories(@Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize, @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {
        return ResponseEntity.ok().body(service.getMainCategories(pageSize, pageNumber));
    }

    @GetMapping(value = "public/logo")
    public ResponseEntity<?> viewMainCategoryLogo(@NotBlank @RequestParam(value = "logo") String logoId) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(service.viewMainCategoryLogo(logoId));
    }
}