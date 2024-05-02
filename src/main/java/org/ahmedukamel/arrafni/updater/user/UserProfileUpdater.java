package org.ahmedukamel.arrafni.updater.user;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.user.UpdateProfileRequest;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class UserProfileUpdater implements BiFunction<User, UpdateProfileRequest, User> {
    final UserRepository repository;

    @Override
    public User apply(User user, UpdateProfileRequest request) {
        user.setFirstName(request.firstName().strip());
        user.setLastName(request.lastName().strip());
        user.setGender(request.gender());
        return repository.save(user);
    }
}