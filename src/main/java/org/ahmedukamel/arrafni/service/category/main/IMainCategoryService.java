package org.ahmedukamel.arrafni.service.category.main;

public interface IMainCategoryService {
    Object createMainCategory(Object object);

    Object updateMainCategory(Integer id, Object object);

    void deleteMainCategory(Integer id);

    Object readMainCategory(Integer id);

    Object readMainCategories(int pageSize, int pageNumber);
}