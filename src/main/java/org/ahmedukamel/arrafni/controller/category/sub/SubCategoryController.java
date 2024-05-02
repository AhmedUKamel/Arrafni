package org.ahmedukamel.arrafni.controller.category.sub;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.dto.category.sub.SubCategoryDto;
import org.ahmedukamel.arrafni.service.category.sub.SubCategoryService;
import org.ahmedukamel.arrafni.service.category.sub.ISubCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/sub-category")
public class SubCategoryController {
    private final ISubCategoryService service;

    public SubCategoryController(SubCategoryService service) {
        this.service = service;
    }

    @AdminAuthorization
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody SubCategoryDto request) {
        return ResponseEntity.created(URI.create("api/v1/sub-category")).body(service.createCategory(request));
    }

    @AdminAuthorization
    @PutMapping(value = "{subCategoryId}")
    public ResponseEntity<?> updateCategory(@Min(value = 1) @PathVariable(value = "subCategoryId") Integer id,
                                            @Valid @RequestBody SubCategoryDto request) {
        return ResponseEntity.ok().body(service.updateCategory(id, request));
    }

    @AdminAuthorization
    @DeleteMapping(value = "{subCategoryId}")
    public ResponseEntity<?> deleteCategory(@Min(value = 1) @PathVariable(value = "subCategoryId") Integer id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "public/{subCategoryId}")
    public ResponseEntity<?> readCategory(@Min(value = 1) @PathVariable(value = "subCategoryId") Integer id) {
        return ResponseEntity.ok().body(service.readCategory(id));
    }

    @GetMapping(value = "public")
    public ResponseEntity<?> readCategories(@Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {
        return ResponseEntity.ok().body(service.readCategories(pageSize, pageNumber));
    }
}