package org.ahmedukamel.sohagy.saver;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ahmedukamel.sohagy.dto.request.RegistrationRequest;
import org.ahmedukamel.sohagy.mapper.phonenumber.PhoneNumberMapper;
import org.ahmedukamel.sohagy.model.PhoneNumber;
import org.ahmedukamel.sohagy.model.User;
import org.ahmedukamel.sohagy.repository.UserRepository;
import org.ahmedukamel.sohagy.service.db.DatabaseService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserSaver implements Function<RegistrationRequest, User> {
    final UserRepository repository;
    final PhoneNumberMapper mapper;
    final PasswordEncoder encoder;

    @SneakyThrows
    @Override
    public User apply(RegistrationRequest request) {
        PhoneNumber number = mapper.apply(request.phone());
        DatabaseService.unique(repository::existsByPhoneNumber, number, User.class);

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhoneNumber(number);
        user.setPassword(encoder.encode(request.password()));
        user.setRegistration(ZonedDateTime.now(ZoneId.of("Z")));
        // TODO: SMS
        user.setEnabled(true);
        return repository.save(user);
    }
}