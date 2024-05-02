package org.ahmedukamel.arrafni.service.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.security.jgss.GSSUtil;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.constant.PathConstants;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.user.UpdateProfileRequest;
import org.ahmedukamel.arrafni.dto.user.UserProfileResponse;
import org.ahmedukamel.arrafni.dto.user.VerifiedProfileData;
import org.ahmedukamel.arrafni.mapper.phonenumber.PhoneNumberMapper;
import org.ahmedukamel.arrafni.mapper.user.UserProfileResponseMapper;
import org.ahmedukamel.arrafni.model.PhoneNumber;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.repository.UserRepository;
import org.ahmedukamel.arrafni.saver.FileSaver;
import org.ahmedukamel.arrafni.updater.user.UserProfileUpdater;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
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
    final PhoneNumberMapper numberMapper;
    final UserProfileUpdater updater;
    final PasswordEncoder encoder;
    final FileSaver fileSaver;

    @Override
    public Object getProfile() {
        User user = ContextHolderUtils.getUser();
        UserProfileResponse response = mapper.apply(user);
        return new ApiResponse(true, "Successfully Get User Profile.", response);
    }

    @Override
    public Object updateProfile(Object object) {
        UpdateProfileRequest request = (UpdateProfileRequest) object;
        User user = ContextHolderUtils.getUser();
        User updatedUser = updater.apply(user, request);
        UserProfileResponse response = mapper.apply(updatedUser);
        return new ApiResponse(true, "Successfully Update User Profile.", response);
    }

    @Override
    public void updatePhone(String phone) {
        User user = ContextHolderUtils.getUser();
        PhoneNumber number = numberMapper.apply(phone);
        // TODO: Send SMS
        VerifiedProfileData verifiedProfileData = new VerifiedProfileData(null, number);
        String change = new Gson().toJson(verifiedProfileData);

    }

    @Override
    public void updatePhone(String phone, int otp) {

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