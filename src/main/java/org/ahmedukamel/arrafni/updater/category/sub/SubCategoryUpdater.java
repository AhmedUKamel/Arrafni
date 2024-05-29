package org.ahmedukamel.arrafni.updater.category.sub;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.category.sub.SubCategoryDto;
import org.ahmedukamel.arrafni.model.MainCategory;
import org.ahmedukamel.arrafni.model.SubCategory;
import org.ahmedukamel.arrafni.repository.MainCategoryRepository;
import org.ahmedukamel.arrafni.repository.SubCategoryRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Repository;

import java.util.function.BiFunction;

@Repository
@RequiredArgsConstructor
public class SubCategoryUpdater implements BiFunction<SubCategory, SubCategoryDto, SubCategory> {
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public SubCategory apply(SubCategory subCategory, SubCategoryDto request) {
        MainCategory mainCategory = DatabaseService.get(mainCategoryRepository::findById, request.mainCategoryId(), MainCategory.class);

        String processedName = request.name().strip();
        if (!subCategory.getName().equalsIgnoreCase(processedName)) {
            DatabaseService.unique(subCategoryRepository::existsByNameIgnoreCase, processedName, SubCategory.class);
        }

        subCategory.setName(processedName);
        subCategory.setMainCategory(mainCategory);

        return subCategoryRepository.save(subCategory);
    }
}