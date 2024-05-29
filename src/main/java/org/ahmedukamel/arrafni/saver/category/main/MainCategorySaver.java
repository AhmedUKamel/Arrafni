package org.ahmedukamel.arrafni.saver.category.main;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.constant.PathConstants;
import org.ahmedukamel.arrafni.model.MainCategory;
import org.ahmedukamel.arrafni.repository.MainCategoryRepository;
import org.ahmedukamel.arrafni.saver.FileSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class MainCategorySaver implements BiFunction<String, MultipartFile, MainCategory> {
    private final MainCategoryRepository repository;
    private final FileSaver fileSaver;

    @Override
    public MainCategory apply(String name, MultipartFile file) {
        String processedName = name.strip();

        DatabaseService.unique(repository::existsByNameIgnoreCase, processedName, MainCategory.class);

        Optional<String> optionalLogoId = fileSaver.apply(file, PathConstants.MAIN_CATEGORY_LOGO);
        if (optionalLogoId.isEmpty()) {
            throw new RuntimeException("Failed to save main category logo.");
        }

        MainCategory mainCategory = new MainCategory();
        mainCategory.setName(processedName);
        mainCategory.setLogo(optionalLogoId.get());

        return repository.save(mainCategory);
    }
}