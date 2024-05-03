package org.ahmedukamel.arrafni.saver.category.main;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.category.main.MainCategoryDto;
import org.ahmedukamel.arrafni.model.MainCategory;
import org.ahmedukamel.arrafni.repository.MainCategoryRepository;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MainCategorySaver implements Function<MainCategoryDto, MainCategory> {
    final MainCategoryRepository repository;

    @Override
    public MainCategory apply(MainCategoryDto request) {
        MainCategory mainCategory = new MainCategory();
        mainCategory.setName(request.name());
        mainCategory.setLogo(request.logo());
        return repository.save(mainCategory);
    }
}