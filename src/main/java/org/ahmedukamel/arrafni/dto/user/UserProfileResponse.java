package org.ahmedukamel.arrafni.dto.user;

import org.ahmedukamel.arrafni.model.enumration.Gender;
import org.ahmedukamel.arrafni.model.enumration.Role;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

public record UserProfileResponse(
        Long id,
        String number,
        String email,
        String firstName,
        String lastName,
        String picture,
        Role role,
        Gender gender,
        Collection<? extends GrantedAuthority> authorities,
        LocalDateTime registration,
        boolean hasEmail,
        boolean hasPicture
) {
}