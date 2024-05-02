package org.ahmedukamel.arrafni.service.category.main;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.category.main.MainCategoryDto;
import org.ahmedukamel.arrafni.dto.category.main.MainCategoryResponse;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.mapper.category.main.MainCategoryResponseMapper;
import org.ahmedukamel.arrafni.model.MainCategory;
import org.ahmedukamel.arrafni.repository.MainCategoryRepository;
import org.ahmedukamel.arrafni.saver.category.main.MainCategorySaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.updater.category.main.MainCategoryUpdater;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainCategoryService implements IMainCategoryService {
    final MainCategoryResponseMapper mapper;
    final MainCategoryRepository repository;
    final MainCategoryUpdater updater;
    final MainCategorySaver saver;

    @Override
    public Object createMainCategory(Object object) {
        MainCategoryDto request = (MainCategoryDto) object;
        MainCategory savedMainCategory = saver.apply(request);
        MainCategoryResponse response = mapper.apply(savedMainCategory);
        return new ApiResponse(true, "Successful Create Main-Category.", response);
    }

    @Override
    public Object updateMainCategory(Integer id, Object object) {
        MainCategory mainCategory = DatabaseService.get(repository::findById, id, MainCategory.class);
        MainCategoryDto request = (MainCategoryDto) object;
        MainCategory updatedMainCategory = updater.apply(mainCategory, request);
        MainCategoryResponse response = mapper.apply(updatedMainCategory);
        return new ApiResponse(true, "Successful Update Main-Category.", response);
    }

    @Override
    public void deleteMainCategory(Integer id) {
        MainCategory mainCategory = DatabaseService.get(repository::findById, id, MainCategory.class);
        if (mainCategory.getSubCategories().isEmpty()) {
            repository.delete(mainCategory);
            return;
        }
        throw new RuntimeException("Main-Category with id %s Contains Sub-Categories and Cannot Be Deleted.".formatted(id));
    }

    @Override
    public Object readMainCategory(Integer id) {
        MainCategory mainCategory = DatabaseService.get(repository::findById, id, MainCategory.class);
        MainCategoryResponse response = mapper.apply(mainCategory);
        return new ApiResponse(true, "Successful Get Main-Category.", response);
    }

    @Override
    public Object readMainCategories(int pageSize, int pageNumber) {
        List<MainCategoryResponse> response = repository
                .selectPaginatedMainCategories(pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Get Main-Categories.", response);
    }
}