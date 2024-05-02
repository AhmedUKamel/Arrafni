package org.ahmedukamel.arrafni.controller.business;

import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.annotation.AdminAuthorization;
import org.ahmedukamel.arrafni.service.business.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AdminAuthorization
@RequestMapping(value = "api/v1/business")
public class AdminBusinessController {
    final IAdminBusinessService service;

    public AdminBusinessController(AdminBusinessService service) {
        this.service = service;
    }

    @PutMapping(value = "{businessId}/lock")
    public ResponseEntity<?> setLockStatus(@Min(value = 1) @PathVariable(value = "businessId") Long id,
                                           @RequestParam(value = "locked") boolean locked) {
        service.setLockStatus(id, locked);
        return ResponseEntity.noContent().build();
    }
}