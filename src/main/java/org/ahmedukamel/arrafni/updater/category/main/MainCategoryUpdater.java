package org.ahmedukamel.arrafni.updater.category.main;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.model.MainCategory;
import org.ahmedukamel.arrafni.repository.MainCategoryRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class MainCategoryUpdater implements BiFunction<MainCategory, String, MainCategory> {
    final MainCategoryRepository repository;

    @Override
    public MainCategory apply(MainCategory mainCategory, String name) {
        String processedName = name.strip();

        if (!mainCategory.getName().equalsIgnoreCase(processedName)) {
            DatabaseService.unique(repository::existsByNameIgnoreCase, processedName, MainCategory.class);
        }

        mainCategory.setName(processedName);

        return repository.save(mainCategory);
    }
}