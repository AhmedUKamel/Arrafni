package org.ahmedukamel.arrafni.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.auth.RegistrationRequest;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.user.UserProfileResponse;
import org.ahmedukamel.arrafni.mapper.user.UserProfileResponseMapper;
import org.ahmedukamel.arrafni.saver.UserSaver;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.repository.UserRepository;
import org.ahmedukamel.arrafni.service.notification.LoginNotifier;
import org.ahmedukamel.arrafni.service.token.IAccessTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    final AuthenticationManager authenticationManager;
    final UserProfileResponseMapper mapper;
    final IAccessTokenService service;
    final LoginNotifier loginNotifier;
    final UserRepository repository;
    final UserSaver saver;

    @Override
    public Object register(Object object) {
        RegistrationRequest request = (RegistrationRequest) object;
        User savedUser = saver.apply(request);
        UserProfileResponse response = mapper.apply(savedUser);
        return new ApiResponse(true, "Successfully User Registration.", response);
    }

    @Transactional
    @Override
    public Object login(String username, String password, HttpServletRequest httpServletRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        if (authentication.getPrincipal() instanceof User user) {
            String jwt = service.generateToken(user);
            loginNotifier.accept(user, httpServletRequest);
            return new ApiResponse(true, "Successfully User Login.", jwt);
        }

        throw new UsernameNotFoundException("Unknown Authentication.");
    }

    @Override
    public Object forgetPassword(String username) {
        return null;
    }

    @Override
    public Object activateAccount(String username) {
        return null;
    }
}