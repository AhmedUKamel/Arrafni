package org.ahmedukamel.arrafni.service.user;

import org.springframework.web.multipart.MultipartFile;

public interface IUserAccountService {
    Object getProfile();

    Object updateProfile(Object object);

    void updatePhone(String phone);

    void updatePhone(String phone, int otp);

    void changePassword(String oldPassword, String newPassword);

    void uploadPicture(MultipartFile file);

    void removePicture();
}