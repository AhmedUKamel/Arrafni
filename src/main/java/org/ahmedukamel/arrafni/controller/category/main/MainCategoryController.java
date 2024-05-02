package org.ahmedukamel.arrafni.controller.category.main;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.dto.category.main.MainCategoryDto;
import org.ahmedukamel.arrafni.service.category.main.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createMainCategory(@Valid @RequestBody MainCategoryDto request) {
        return ResponseEntity.created(URI.create("api/v1/main-category")).body(service.createMainCategory(request));
    }

    @AdminAuthorization
    @PutMapping(value = "{mainCategoryId}")
    public ResponseEntity<?> updateMainCategory(@Min(value = 1) @PathVariable(value = "mainCategoryId") Integer id,
                                                @Valid @RequestBody MainCategoryDto request) {
        return ResponseEntity.ok().body(service.updateMainCategory(id, request));
    }

    @AdminAuthorization
    @DeleteMapping(value = "{mainCategoryId}")
    public ResponseEntity<?> deleteMainCategory(@Min(value = 1) @PathVariable(value = "mainCategoryId") Integer id) {
        service.deleteMainCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "public/{mainCategoryId}")
    public ResponseEntity<?> readMainCategory(@Min(value = 1) @PathVariable(value = "mainCategoryId") Integer id) {
        return ResponseEntity.ok().body(service.readMainCategory(id));
    }

    @GetMapping(value = "public")
    public ResponseEntity<?> readMainCategories(@Min(value = 1) @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                                @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") int pageNumber) {
        return ResponseEntity.ok().body(service.readMainCategories(pageSize, pageNumber));
    }
}