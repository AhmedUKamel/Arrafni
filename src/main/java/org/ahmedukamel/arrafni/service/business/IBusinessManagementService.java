package org.ahmedukamel.arrafni.service.business;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBusinessManagementService {
    Object createBusiness(Object object, MultipartFile logoFile, MultipartFile[] picturesFiles);

    Object readBusiness(Long id);

    Object updateBusiness(Long id, Object object);

    void deleteBusiness(Long id);

    void uploadLogo(Long id, MultipartFile file);

    void uploadImages(Long id, MultipartFile[] files);

    void deleteImages(Long id, List<String> imagesName);
}