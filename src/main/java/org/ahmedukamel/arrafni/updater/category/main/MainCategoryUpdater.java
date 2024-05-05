package org.ahmedukamel.arrafni.updater.category.main;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.category.main.MainCategoryDto;
import org.ahmedukamel.arrafni.model.MainCategory;
import org.ahmedukamel.arrafni.repository.MainCategoryRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class MainCategoryUpdater implements BiFunction<MainCategory, MainCategoryDto, MainCategory> {
    final MainCategoryRepository repository;

    @Override
    public MainCategory apply(MainCategory mainCategory, MainCategoryDto request) {
        String name = request.name().strip();
        if (!mainCategory.getName().equalsIgnoreCase(name)) {
            DatabaseService.unique(repository::existsByNameIgnoreCase, name, MainCategory.class);
        }
        mainCategory.setName(name);
        mainCategory.setLogo(request.logo().strip());
        return repository.save(mainCategory);
    }
}