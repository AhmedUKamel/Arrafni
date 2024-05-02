package org.ahmedukamel.arrafni.service.category.sub;

public interface ISubCategoryService {
    Object createCategory(Object object);

    Object updateCategory(Integer id, Object object);

    void deleteCategory(Integer id);

    Object readCategory(Integer id);

    Object readCategories(int pageSize, int pageNumber);
}