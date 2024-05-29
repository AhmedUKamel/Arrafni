package org.ahmedukamel.arrafni.controller.category.sub;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.annotation.NotEmpty;
import org.ahmedukamel.arrafni.dto.category.sub.SubCategoryDto;
import org.ahmedukamel.arrafni.service.category.sub.SubCategoryService;
import org.ahmedukamel.arrafni.service.category.sub.ISubCategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Validated
@RestController
@RequestMapping(value = "api/v1/sub-category")
public class SubCategoryController {
    private final ISubCategoryService service;

    public SubCategoryController(SubCategoryService service) {
        this.service = service;
    }

    @AdminAuthorization
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestParam(value = "subCategory") SubCategoryDto request,
                                            @NotEmpty @RequestParam(value = "logo") MultipartFile logo) {
        return ResponseEntity.created(URI.create("api/v1/sub-category")).body(service.createCategory(request, logo));
    }

    @AdminAuthorization
    @PutMapping(value = "{subCategoryId}")
    public ResponseEntity<?> updateCategory(@Min(value = 1) @PathVariable(value = "subCategoryId") Integer id,
                                            @Valid @RequestBody SubCategoryDto request) {
        return ResponseEntity.ok().body(service.updateCategory(id, request));
    }

    @AdminAuthorization
    @PutMapping(value = "{subCategoryId}/logo")
    public ResponseEntity<?> uploadCategoryLogo(@Min(value = 1) @PathVariable(value = "subCategoryId") Integer id,
                                                @NotEmpty @RequestParam(value = "logo") MultipartFile logo) {
        return ResponseEntity.ok().body(service.uploadCategoryLogo(id, logo));
    }

    @AdminAuthorization
    @DeleteMapping(value = "{subCategoryId}")
    public ResponseEntity<?> deleteCategory(@Min(value = 1) @PathVariable(value = "subCategoryId") Integer id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "public/{subCategoryId}")
    public ResponseEntity<?> readCategory(@Min(value = 1) @PathVariable(value = "subCategoryId") Integer id) {
        return ResponseEntity.ok().body(service.getCategory(id));
    }

    @GetMapping(value = "public")
    public ResponseEntity<?> readCategories(@Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {
        return ResponseEntity.ok().body(service.getCategories(pageSize, pageNumber));
    }

    @GetMapping(value = "public/logo")
    public ResponseEntity<?> viewMainCategoryLogo(@NotBlank @RequestParam(value = "logo") String logoId) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(service.viewSubCategoryLogo(logoId));
    }
}