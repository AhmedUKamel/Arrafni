package org.ahmedukamel.arrafni.controller.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.ahmedukamel.arrafni.annotation.NotEmpty;
import org.ahmedukamel.arrafni.constant.RegexConstants;
import org.ahmedukamel.arrafni.dto.user.UpdateProfileRequest;
import org.ahmedukamel.arrafni.service.user.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "api/v1/account")
public class UserAccountController {
    private final IUserAccountService service;

    public UserAccountController(UserAccountService service) {
        this.service = service;
    }

    @GetMapping(value = "profile")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok().body(service.getProfile());
    }

    @PutMapping(value = "profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.accepted().body(service.updateProfile(request));
    }

    @PutMapping(value = "change-password")
    public ResponseEntity<?> changePassword(@NotBlank @RequestParam(value = "old-password") String oldPassword,
                                            @NotBlank @Pattern(regexp = RegexConstants.PASSWORD) @RequestParam(value = "new-password") String newPassword) {
        service.changePassword(oldPassword, newPassword);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "profile/picture")
    public ResponseEntity<?> uploadPicture(@NotEmpty @RequestParam(value = "picture") MultipartFile file) {
        service.uploadPicture(file);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "profile/picture")
    public ResponseEntity<?> removePicture() {
        service.removePicture();
        return ResponseEntity.noContent().build();
    }
}