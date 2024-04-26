package org.ahmedukamel.sohagy.service.impl;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ahmedukamel.sohagy.constant.ExceptionConstants;
import org.ahmedukamel.sohagy.model.PhoneNumber;
import org.ahmedukamel.sohagy.model.User;
import org.ahmedukamel.sohagy.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    final UserRepository repository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.google.i18n.phonenumbers.Phonenumber.PhoneNumber phonenumber =
                PhoneNumberUtil.getInstance().parse(username, "EG");
        PhoneNumber number = new PhoneNumber(phonenumber.getCountryCode(), phonenumber.getNationalNumber());
        return repository.findByPhoneNumber(number)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionConstants.EntityNotFound.formatted(number, User.class.getSimpleName())));
    }
}