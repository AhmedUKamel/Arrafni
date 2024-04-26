package org.ahmedukamel.sohagy.controller.business;

import org.ahmedukamel.sohagy.annotation.AdminAuthorization;
import org.ahmedukamel.sohagy.service.business.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AdminAuthorization
@RestController
@RequestMapping(value = "api/v1/business")
public class AdminBusinessController {
    final IAdminBusinessService service;

    public AdminBusinessController(AdminBusinessService service) {
        this.service = service;
    }

    @PutMapping(value = "lock/{businessId}")
    public ResponseEntity<?> setLockStatus(@PathVariable(value = "businessId") Long id,
                                           @RequestParam(value = "locked") boolean locked) {
        service.setLockStatus(id, locked);
        return ResponseEntity.noContent().build();
    }
}