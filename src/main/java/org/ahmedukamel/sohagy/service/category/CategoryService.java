package org.ahmedukamel.sohagy.service.category;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.sohagy.dto.category.CategoryResponse;
import org.ahmedukamel.sohagy.dto.response.ApiResponse;
import org.ahmedukamel.sohagy.mapper.category.CategoryResponseMapper;
import org.ahmedukamel.sohagy.model.Category;
import org.ahmedukamel.sohagy.repository.CategoryRepository;
import org.ahmedukamel.sohagy.service.db.DatabaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    final CategoryResponseMapper mapper;
    final CategoryRepository repository;

    @Override
    public Object createCategory(Object object) {
        Category category = new Category();
        BeanUtils.copyProperties(object, category);
        Category savedCategory = repository.save(category);
        CategoryResponse response = mapper.apply(savedCategory);
        return new ApiResponse(true, "Successful Create Category.", response);
    }

    @Override
    public Object updateCategory(Integer id, Object object) {
        Category category = DatabaseService.get(repository::findById, id, Category.class);
        BeanUtils.copyProperties(object, category);
        Category savedCategory = repository.save(category);
        CategoryResponse response = mapper.apply(savedCategory);
        return new ApiResponse(true, "Successful Update Category.", response);
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = DatabaseService.get(repository::findById, id, Category.class);
        repository.delete(category);
    }


    @Override
    public Object readCategory(Integer id) {
        Category category = DatabaseService.get(repository::findById, id, Category.class);
        CategoryResponse response = mapper.apply(category);
        return new ApiResponse(true, "Successful Get Category.", response);
    }


    @Override
    public Object readCategories() {
        List<CategoryResponse> responses = repository.findAll()
                .stream()
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Get Categories.", responses);
    }


    @Override
    public Object readCategories(Integer pageSize, Integer pageNumber) {
        final List<CategoryResponse> response;
        if (pageSize < 1 && pageNumber < 1) {
            response = Collections.emptyList();
        } else {
            response = repository.selectCategories(pageNumber, pageSize * (pageNumber - 1))
                    .stream()
                    .map(mapper)
                    .toList();
        }
        return new ApiResponse(true, "Successful Get Categories.", response);
    }
}