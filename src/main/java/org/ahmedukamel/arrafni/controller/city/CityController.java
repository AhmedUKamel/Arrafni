package org.ahmedukamel.arrafni.controller.city;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.dto.city.CityDto;
import org.ahmedukamel.arrafni.service.city.CityService;
import org.ahmedukamel.arrafni.service.city.ICityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/city")
public class CityController {
    private final ICityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @AdminAuthorization
    @PostMapping
    public ResponseEntity<?> createCity(@Valid @RequestBody CityDto request) {
        return ResponseEntity.created(URI.create("api/v1/city")).body(service.createCity(request));
    }

    @AdminAuthorization
    @PutMapping(value = "{cityId}")
    public ResponseEntity<?> updateCity(@Min(value = 1) @PathVariable(value = "cityId") Integer id,
                                        @Valid @RequestBody CityDto request) {
        return ResponseEntity.ok().body(service.updateCity(id, request));
    }

    @AdminAuthorization
    @DeleteMapping(value = "{cityId}")
    public ResponseEntity<?> deleteCity(@Min(value = 1) @PathVariable(value = "cityId") Integer id) {
        service.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "public")
    public ResponseEntity<?> getCities() {
        return ResponseEntity.ok().body(service.getCities());
    }

    @GetMapping(value = "public/search")
    public ResponseEntity<?> searchCities(@NotBlank @RequestParam(value = "word") String word) {
        return ResponseEntity.ok().body(service.searchCities(word));
    }

    @GetMapping(value = "public/{cityId}")
    public ResponseEntity<?> getCity(@Min(value = 1) @PathVariable(value = "cityId") Integer id) {
        return ResponseEntity.ok().body(service.getCity(id));
    }
}