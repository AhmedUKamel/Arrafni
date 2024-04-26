package org.ahmedukamel.sohagy.controller.category;

import jakarta.validation.Valid;
import org.ahmedukamel.sohagy.annotation.AdminAuthorization;
import org.ahmedukamel.sohagy.dto.category.CategoryDto;
import org.ahmedukamel.sohagy.service.category.CategoryService;
import org.ahmedukamel.sohagy.service.category.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/category")
public class CategoryController {
    private final ICategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @AdminAuthorization
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto request) {
        return ResponseEntity.created(URI.create("")).body(service.createCategory(request));
    }

    @AdminAuthorization
    @PutMapping(value = "{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable(value = "categoryId") Integer id, @Valid @RequestBody CategoryDto request) {
        return ResponseEntity.ok().body(service.updateCategory(id, request));
    }

    @AdminAuthorization
    @DeleteMapping(value = "{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "categoryId") Integer id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "public/{categoryId}")
    public ResponseEntity<?> readCategory(@PathVariable(value = "categoryId") Integer id) {
        return ResponseEntity.ok().body(service.readCategory(id));
    }

    @GetMapping(value = "public")
    public ResponseEntity<?> readCategories(@RequestParam(value = "size", required = false) Optional<Integer> pageSize,
                                            @RequestParam(value = "number", required = false) Optional<Integer> pageNumber) {
        return ResponseEntity.ok().body(
                (pageSize.isPresent() && pageNumber.isPresent()) ?
                        service.readCategories(pageSize.get(), pageNumber.get()) :
                        service.readCategories()
        );
    }
}