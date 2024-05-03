package org.ahmedukamel.arrafni.saver.category.sub;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.category.sub.SubCategoryDto;
import org.ahmedukamel.arrafni.model.MainCategory;
import org.ahmedukamel.arrafni.model.SubCategory;
import org.ahmedukamel.arrafni.repository.MainCategoryRepository;
import org.ahmedukamel.arrafni.repository.SubCategoryRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class SubCategorySaver implements Function<SubCategoryDto, SubCategory> {
    final MainCategoryRepository mainCategoryRepository;
    final SubCategoryRepository subCategoryRepository;

    @Override
    public SubCategory apply(SubCategoryDto request) {
        MainCategory mainCategory = DatabaseService.get(mainCategoryRepository::findById, request.mainCategoryId(), MainCategory.class);
        SubCategory subCategory = new SubCategory();
        subCategory.setName(request.name().strip());
        subCategory.setLogo(request.logo().strip());
        subCategory.setMainCategory(mainCategory);
        return subCategoryRepository.save(subCategory);
    }
}