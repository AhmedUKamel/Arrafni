package org.ahmedukamel.arrafni.service.category.main;

import org.springframework.web.multipart.MultipartFile;

public interface IMainCategoryService {
    Object createMainCategory(String name, MultipartFile logo);

    Object updateMainCategory(Integer id, String name);

    Object uploadMainCategoryLogo(Integer id, MultipartFile logo);

    void deleteMainCategory(Integer id);

    Object getMainCategory(Integer id);

    Object getMainCategories(int pageSize, int pageNumber);

    Object viewMainCategoryLogo(String logoId);
}