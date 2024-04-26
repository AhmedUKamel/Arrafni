package org.ahmedukamel.sohagy.service.user;

import org.springframework.web.multipart.MultipartFile;

public interface IUserAccountService {
    Object getProfile();

    Object updateProfile(Object object);

    void changePassword(String oldPassword, String newPassword);

    void uploadPicture(MultipartFile file);

    void removePicture();
}