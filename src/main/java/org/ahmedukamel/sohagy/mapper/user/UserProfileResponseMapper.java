package org.ahmedukamel.sohagy.mapper.user;

import org.ahmedukamel.sohagy.dto.user.UserProfileResponse;
import org.ahmedukamel.sohagy.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.function.Function;

@Component
public class UserProfileResponseMapper implements Function<User, UserProfileResponse> {
    @Override
    public UserProfileResponse apply(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPicture(),
                user.getRole(),
                user.getGender(),
                user.getAuthorities(),
                user.getRegistration(),
                StringUtils.hasLength(user.getEmail()),
                StringUtils.hasLength(user.getPicture())
        );
    }
}