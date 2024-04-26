package org.ahmedukamel.sohagy.dto.user;

import org.ahmedukamel.sohagy.model.enumration.Gender;
import org.ahmedukamel.sohagy.model.enumration.Role;
import org.springframework.security.core.GrantedAuthority;

import java.time.ZonedDateTime;
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
        ZonedDateTime registration,
        boolean hasEmail,
        boolean hasPicture
) {
}