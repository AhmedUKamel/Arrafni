package org.ahmedukamel.arrafni.service.category.sub;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.category.sub.SubCategoryDto;
import org.ahmedukamel.arrafni.dto.category.sub.SubCategoryResponse;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.mapper.category.sub.SubCategoryResponseMapper;
import org.ahmedukamel.arrafni.model.SubCategory;
import org.ahmedukamel.arrafni.repository.SubCategoryRepository;
import org.ahmedukamel.arrafni.saver.category.sub.SubCategorySaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.updater.category.sub.SubCategoryUpdater;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryService implements ISubCategoryService {
    final SubCategoryResponseMapper mapper;
    final SubCategoryRepository repository;
    final SubCategoryUpdater updater;
    final SubCategorySaver saver;

    @Override
    public Object createCategory(Object object) {
        SubCategoryDto request = (SubCategoryDto) object;
        SubCategory savedSubCategory = saver.apply(request);
        SubCategoryResponse response = mapper.apply(savedSubCategory);
        return new ApiResponse(true, "Successful Create Sub-Category.", response);
    }

    @Override
    public Object updateCategory(Integer id, Object object) {
        SubCategory subCategory = DatabaseService.get(repository::findById, id, SubCategory.class);
        SubCategoryDto request = (SubCategoryDto) object;
        SubCategory updatedSubCategory = updater.apply(subCategory, request);
        SubCategoryResponse response = mapper.apply(updatedSubCategory);
        return new ApiResponse(true, "Successful Update Sub-Category.", response);
    }

    @Override
    public void deleteCategory(Integer id) {
        SubCategory subCategory = DatabaseService.get(repository::findById, id, SubCategory.class);
        if (subCategory.getBusinesses().isEmpty()) {
            repository.delete(subCategory);
            return;
        }
        throw new RuntimeException("Sub-Category with id %s Contains Business and Cannot Be Deleted.".formatted(id));
    }


    @Override
    public Object readCategory(Integer id) {
        SubCategory subCategory = DatabaseService.get(repository::findById, id, SubCategory.class);
        SubCategoryResponse response = mapper.apply(subCategory);
        return new ApiResponse(true, "Successful Get Sub-Category.", response);
    }

    @Override
    public Object readCategories(int pageSize, int pageNumber) {
        final List<SubCategoryResponse> response = repository.selectPaginatedSubCategories(pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Get Sub-Categories.", response);
    }
}