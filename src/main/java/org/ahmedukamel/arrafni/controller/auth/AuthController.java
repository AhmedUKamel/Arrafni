package org.ahmedukamel.arrafni.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.ahmedukamel.arrafni.dto.auth.RegistrationRequest;
import org.ahmedukamel.arrafni.service.auth.AuthService;
import org.ahmedukamel.arrafni.service.auth.IAuthService;
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
        return ResponseEntity.created(URI.create("api/v1/auth")).body(service.register(request));
    }

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestParam(value = "phone") String username,
                                   @RequestParam(value = "password") String password,
                                   HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(service.login(username, password, httpServletRequest));
    }

    @PostMapping(value = "account-activation")
    public ResponseEntity<?> activateAccount(@RequestParam(value = "phone") String username) {
        return ResponseEntity.accepted().body(service.activateAccount(username));
    }
}