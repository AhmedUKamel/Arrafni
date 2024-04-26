package org.ahmedukamel.sohagy.service.category;

public interface ICategoryService {
    Object createCategory(Object object);

    Object updateCategory(Integer id, Object object);

    void deleteCategory(Integer id);

    Object readCategory(Integer id);

    Object readCategories();

    Object readCategories(Integer pageSize, Integer pageNumber);
}