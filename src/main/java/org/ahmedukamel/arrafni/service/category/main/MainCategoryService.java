package org.ahmedukamel.arrafni.service.category.main;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.constant.PathConstants;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.category.main.MainCategoryResponse;
import org.ahmedukamel.arrafni.mapper.category.main.MainCategoryResponseMapper;
import org.ahmedukamel.arrafni.model.MainCategory;
import org.ahmedukamel.arrafni.repository.MainCategoryRepository;
import org.ahmedukamel.arrafni.saver.FileSaver;
import org.ahmedukamel.arrafni.saver.category.main.MainCategorySaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.updater.category.main.MainCategoryUpdater;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainCategoryService implements IMainCategoryService {
    private final MainCategoryResponseMapper mapper;
    private final MainCategoryRepository repository;
    private final MainCategoryUpdater updater;
    private final MainCategorySaver saver;
    private final FileSaver fileSaver;

    @Override
    public Object createMainCategory(String name, MultipartFile logo) {
        MainCategory savedMainCategory = saver.apply(name, logo);
        MainCategoryResponse response = mapper.apply(savedMainCategory);
        return new ApiResponse(true, "Successful Create Main-Category.", response);
    }

    @Override
    public Object updateMainCategory(Integer id, String name) {
        MainCategory mainCategory = DatabaseService.get(repository::findById, id, MainCategory.class);
        MainCategory updatedMainCategory = updater.apply(mainCategory, name);
        MainCategoryResponse response = mapper.apply(updatedMainCategory);
        return new ApiResponse(true, "Successful Update Main-Category.", response);
    }

    @Override
    public Object uploadMainCategoryLogo(Integer id, MultipartFile logo) {
        MainCategory mainCategory = DatabaseService.get(repository::findById, id, MainCategory.class);

        try {
            Files.deleteIfExists(PathConstants.MAIN_CATEGORY_LOGO.resolve(mainCategory.getLogo()));

            Optional<String> optionalLogoId = fileSaver.apply(logo, PathConstants.MAIN_CATEGORY_LOGO);

            if (optionalLogoId.isEmpty()) {
                throw new IOException();
            }

            mainCategory.setLogo(optionalLogoId.get());

            MainCategory updatedMainCategory = repository.save(mainCategory);

            MainCategoryResponse response = mapper.apply(updatedMainCategory);
            return new ApiResponse(true, "Successful Upload Main-Category.", response);

        } catch (IOException exception) {
            throw new RuntimeException("Failed to upload main category logo", exception);
        }
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
    public Object getMainCategory(Integer id) {
        MainCategory mainCategory = DatabaseService.get(repository::findById, id, MainCategory.class);
        MainCategoryResponse response = mapper.apply(mainCategory);
        return new ApiResponse(true, "Successful Get Main-Category.", response);
    }

    @Override
    public Object getMainCategories(int pageSize, int pageNumber) {
        List<MainCategoryResponse> response = repository
                .selectPaginatedMainCategories(pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Get Main-Categories.", response);
    }

    @Override
    public Object viewMainCategoryLogo(String logoId) {
        final byte[] image;

        try {
            Path path = PathConstants.MAIN_CATEGORY_LOGO.resolve(logoId.strip());

            if (Files.exists(path)) {
                image = Files.readAllBytes(path);
            } else {
                image = new ClassPathResource("static/images/image-not-found.png")
                        .getContentAsByteArray();
            }

        } catch (IOException exception) {
            throw new RuntimeException("Failed to read main category logo", exception);
        }

        return image;
    }
}