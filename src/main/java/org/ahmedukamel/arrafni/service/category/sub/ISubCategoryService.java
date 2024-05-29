package org.ahmedukamel.arrafni.service.category.sub;

import org.springframework.web.multipart.MultipartFile;

public interface ISubCategoryService {
    Object createCategory(Object object, MultipartFile file);

    Object updateCategory(Integer id, Object object);

    Object uploadCategoryLogo(Integer id, MultipartFile file);

    void deleteCategory(Integer id);

    Object getCategory(Integer id);

    Object getCategories(int pageSize, int pageNumber);

    Object viewSubCategoryLogo(String logoId);
}