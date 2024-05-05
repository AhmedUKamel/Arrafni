package org.ahmedukamel.arrafni.controller.region;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.dto.region.RegionDto;
import org.ahmedukamel.arrafni.service.region.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/region")
public class RegionController {
    private final IRegionService service;

    public RegionController(RegionService service) {
        this.service = service;
    }

    @AdminAuthorization
    @PostMapping
    public ResponseEntity<?> createRegion(@Valid @RequestBody RegionDto request) {
        return ResponseEntity.created(URI.create("api/v1/region")).body(service.createRegion(request));
    }

    @AdminAuthorization
    @PutMapping(value = "{regionId}")
    public ResponseEntity<?> updateRegion(@Min(value = 1) @PathVariable(value = "regionId") Integer id,
                                          @Valid @RequestBody RegionDto request) {
        return ResponseEntity.ok().body(service.updateRegion(id, request));
    }

    @AdminAuthorization
    @DeleteMapping(value = "{regionId}")
    public ResponseEntity<?> deleteRegion(@Min(value = 1) @PathVariable(value = "regionId") Integer id) {
        service.deleteRegion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "public")
    public ResponseEntity<?> getRegions() {
        return ResponseEntity.ok().body(service.getRegions());
    }

    @GetMapping(value = "public/search")
    public ResponseEntity<?> searchRegions(@NotBlank @RequestParam(value = "word") String word) {
        return ResponseEntity.ok().body(service.searchRegions(word));
    }

    @GetMapping(value = "public/{regionId}")
    public ResponseEntity<?> getRegion(@Min(value = 1) @PathVariable(value = "regionId") Integer id) {
        return ResponseEntity.ok().body(service.getRegion(id));
    }
}