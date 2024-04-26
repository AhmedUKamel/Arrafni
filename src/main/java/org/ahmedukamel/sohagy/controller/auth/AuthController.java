package org.ahmedukamel.sohagy.controller.auth;

import jakarta.validation.Valid;
import org.ahmedukamel.sohagy.dto.request.RegistrationRequest;
import org.ahmedukamel.sohagy.service.auth.AuthService;
import org.ahmedukamel.sohagy.service.auth.IAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {
    private final IAuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping(value = "register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.created(URI.create("")).body(service.register(request));
    }

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestParam(value = "username") String username,
                                   @RequestParam(value = "password") String password) {
        return ResponseEntity.ok().body(service.login(username, password));
    }
}