package org.ahmedukamel.sohagy.service.user;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.sohagy.constant.PathConstants;
import org.ahmedukamel.sohagy.dto.response.ApiResponse;
import org.ahmedukamel.sohagy.dto.user.UserProfileResponse;
import org.ahmedukamel.sohagy.mapper.user.UserProfileResponseMapper;
import org.ahmedukamel.sohagy.model.User;
import org.ahmedukamel.sohagy.repository.UserRepository;
import org.ahmedukamel.sohagy.saver.FileSaver;
import org.ahmedukamel.sohagy.util.ContextHolderUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountService implements IUserAccountService {
    final UserRepository repository;
    final UserProfileResponseMapper mapper;
    final PasswordEncoder encoder;
    final FileSaver fileSaver;

    @Override
    public Object getProfile() {
        UserProfileResponse response = mapper.apply(ContextHolderUtils.getUser());
        return new ApiResponse(true, "Successfully Get User Profile.", response);
    }

    @Override
    public Object updateProfile(Object object) {
        return null;
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        User user = ContextHolderUtils.getUser();

        if (oldPassword.equals(newPassword)) {
            throw new RuntimeException("New password cannot be old one.");
        }
        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        user.setPassword(encoder.encode(newPassword));
        repository.save(user);
    }

    @Override
    public void uploadPicture(MultipartFile file) {
        User user = ContextHolderUtils.getUser();
        if (StringUtils.hasLength(user.getPicture())) {
            try {
                Files.delete(PathConstants.USER_PROFILE_PICTURE.resolve(user.getPicture()));
            } catch (IOException ignored) {
            }
        }

        Optional<String> result = fileSaver.apply(file, PathConstants.USER_PROFILE_PICTURE);
        if (result.isEmpty()) {
            throw new RuntimeException("Error saving image.");
        }

        result.ifPresent(user::setPicture);
        repository.save(user);
    }

    @Override
    public void removePicture() {
        User user = ContextHolderUtils.getUser();
        if (StringUtils.hasLength(user.getPicture())) {
            try {
                Files.delete(PathConstants.USER_PROFILE_PICTURE.resolve(user.getPicture()));
                user.setPicture(null);
                repository.save(user);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}