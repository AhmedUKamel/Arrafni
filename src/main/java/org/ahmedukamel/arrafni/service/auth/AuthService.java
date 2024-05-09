package org.ahmedukamel.arrafni.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.auth.RegistrationRequest;
import org.ahmedukamel.arrafni.dto.user.UserProfileResponse;
import org.ahmedukamel.arrafni.generator.AccountActivationTokenGenerator;
import org.ahmedukamel.arrafni.mapper.phonenumber.PhoneNumberMapper;
import org.ahmedukamel.arrafni.mapper.user.UserProfileResponseMapper;
import org.ahmedukamel.arrafni.model.AccountToken;
import org.ahmedukamel.arrafni.model.PhoneNumber;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.repository.UserRepository;
import org.ahmedukamel.arrafni.saver.UserSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.service.sms.AccountActivationSMSSender;
import org.ahmedukamel.arrafni.service.token.IAccessTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    final AuthenticationManager authenticationManager;
    final AccountActivationTokenGenerator generator;
    final PhoneNumberMapper phoneNumberMapper;
    final AccountActivationSMSSender sender;
    final UserProfileResponseMapper mapper;
    final IAccessTokenService service;
    final UserRepository repository;
    final UserSaver saver;

    final ExecutorService executor = Executors.newFixedThreadPool(2);

    @Override
    public Object register(Object object) {
        RegistrationRequest request = (RegistrationRequest) object;
        User savedUser = saver.apply(request);
        CompletableFuture<AccountToken> tokenFuture = CompletableFuture.supplyAsync(
                () -> generator.apply(savedUser), executor);
        tokenFuture.thenAcceptAsync((accountToken) -> sender.sendSMS(
                accountToken.getUser().getPhoneNumber(),
                accountToken.getOtp().toString()), executor);
        UserProfileResponse response = mapper.apply(savedUser);
        return new ApiResponse(true, "Successful User Registration.", response);
    }

    @Transactional
    @Override
    public Object login(String username, String password, HttpServletRequest httpServletRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        if (authentication.getPrincipal() instanceof User user) {
            String jwt = service.generateToken(user);
            return new ApiResponse(true, "Successful User Login.", jwt);
        }

        throw new UsernameNotFoundException("Unknown Authentication.");
    }

    @Override
    public Object forgetPassword(String username) {
        return null;
    }

    @Override
    public Object activateAccount(String username) {
        PhoneNumber phoneNumber = phoneNumberMapper.apply(username);
        User user = DatabaseService.get(repository::findByPhoneNumber, phoneNumber, User.class);
        if (user.isEnabled()) {
            throw new IllegalArgumentException("User with phone number %s is already enabled.".formatted(phoneNumber));
        }
        CompletableFuture<AccountToken> tokenFuture = CompletableFuture.supplyAsync(
                () -> generator.apply(user), executor);
        tokenFuture.thenAcceptAsync((accountToken) -> sender.sendSMS(
                accountToken.getUser().getPhoneNumber(),
                accountToken.getOtp().toString()), executor);
        return new ApiResponse(true, "Activation Account SMS Sent Successfully.", "");
    }
}