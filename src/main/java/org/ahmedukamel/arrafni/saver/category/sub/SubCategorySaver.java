package org.ahmedukamel.arrafni.saver.category.sub;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.constant.PathConstants;
import org.ahmedukamel.arrafni.dto.category.sub.SubCategoryDto;
import org.ahmedukamel.arrafni.model.MainCategory;
import org.ahmedukamel.arrafni.model.SubCategory;
import org.ahmedukamel.arrafni.repository.MainCategoryRepository;
import org.ahmedukamel.arrafni.repository.SubCategoryRepository;
import org.ahmedukamel.arrafni.saver.FileSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class SubCategorySaver implements BiFunction<SubCategoryDto, MultipartFile, SubCategory> {
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final FileSaver fileSaver;

    @Override
    public SubCategory apply(SubCategoryDto request, MultipartFile file) {
        MainCategory mainCategory = DatabaseService.get(mainCategoryRepository::findById, request.mainCategoryId(), MainCategory.class);

        String processedName = request.name().strip();
        DatabaseService.unique(subCategoryRepository::existsByNameIgnoreCase, processedName, SubCategory.class);

        Optional<String> optionalLogoId = fileSaver.apply(file, PathConstants.SUB_CATEGORY_LOGO);
        if (optionalLogoId.isEmpty()) {
            throw new RuntimeException("Failed to save sub category logo");
        }

        SubCategory subCategory = new SubCategory();
        subCategory.setName(processedName);
        subCategory.setLogo(optionalLogoId.get());
        subCategory.setMainCategory(mainCategory);

        return subCategoryRepository.save(subCategory);
    }
}