package org.ahmedukamel.sohagy.service.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.sohagy.dto.request.RegistrationRequest;
import org.ahmedukamel.sohagy.dto.response.ApiResponse;
import org.ahmedukamel.sohagy.dto.user.UserProfileResponse;
import org.ahmedukamel.sohagy.mapper.user.UserProfileResponseMapper;
import org.ahmedukamel.sohagy.saver.UserSaver;
import org.ahmedukamel.sohagy.model.User;
import org.ahmedukamel.sohagy.repository.UserRepository;
import org.ahmedukamel.sohagy.service.token.IAccessTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    final AuthenticationManager authenticationManager;
    final UserRepository repository;
    final IAccessTokenService service;
    final UserSaver saver;
    final UserProfileResponseMapper mapper;

    @Override
    public Object register(Object object) {
        RegistrationRequest request = (RegistrationRequest) object;
        User savedUser = saver.apply(request);
        UserProfileResponse response = mapper.apply(savedUser);
        return new ApiResponse(true, "Successfully User Registration.", response);
    }

    @Transactional
    @Override
    public Object login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        if (authentication.getPrincipal() instanceof User user) {
            String jwt = service.generateToken(user);
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