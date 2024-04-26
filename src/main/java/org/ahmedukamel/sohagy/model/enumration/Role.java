package org.ahmedukamel.sohagy.model.enumration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    USER(100, "ROLE_USER"),
    ADMIN(200, "ROLE_ADMIN");

    private final int id;
    private final String name;

    @Override
    public String getAuthority() {
        return name;
    }
}