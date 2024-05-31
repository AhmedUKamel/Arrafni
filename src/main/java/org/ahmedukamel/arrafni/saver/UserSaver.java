package org.ahmedukamel.arrafni.saver;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ahmedukamel.arrafni.dto.auth.RegistrationRequest;
import org.ahmedukamel.arrafni.mapper.phonenumber.PhoneNumberMapper;
import org.ahmedukamel.arrafni.model.Region;
import org.ahmedukamel.arrafni.model.Wishlist;
import org.ahmedukamel.arrafni.model.embeddable.PhoneNumber;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.repository.RegionRepository;
import org.ahmedukamel.arrafni.repository.UserRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserSaver implements Function<RegistrationRequest, User> {
    private final RegionRepository regionRepository;
    private final UserRepository repository;
    private final PhoneNumberMapper mapper;
    private final PasswordEncoder encoder;

    @SneakyThrows
    @Override
    public User apply(RegistrationRequest request) {
        PhoneNumber number = mapper.apply(request.phone());
        DatabaseService.unique(repository::existsByPhoneNumber, number, User.class);

        Region region = DatabaseService.get(regionRepository::findById, request.regionId(), Region.class);

        User user = new User();
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        user.setWishlist(wishlist);
        user.setFirstName(request.firstName().strip());
        user.setLastName(request.lastName().strip());
        user.setPhoneNumber(number);
        user.setPassword(encoder.encode(request.password()));
        user.setRegion(region);
        // TODO: SMS
        user.setEnabled(true);
        return repository.save(user);
    }
}