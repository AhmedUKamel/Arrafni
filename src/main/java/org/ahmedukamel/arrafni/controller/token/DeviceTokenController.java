package org.ahmedukamel.arrafni.controller.token;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.ahmedukamel.arrafni.dto.token.DeviceTokenRequest;
import org.ahmedukamel.arrafni.service.token.DeviceTokenService;
import org.ahmedukamel.arrafni.service.token.IDeviceTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/public/device-token")
public class DeviceTokenController {
    private final IDeviceTokenService service;

    public DeviceTokenController(DeviceTokenService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<?> updateDeviceToken(
            @Valid @RequestBody DeviceTokenRequest request) {

        return ResponseEntity.accepted().body(service.updateDeviceToken(request));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDeviceToken(
            @NotEmpty @RequestParam(value = "token") String token) {

        return ResponseEntity.accepted().body(service.deleteDeviceToken(token));
    }
}
