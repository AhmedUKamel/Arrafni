package org.ahmedukamel.arrafni.service.category.sub;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.constant.PathConstants;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.category.sub.SubCategoryDto;
import org.ahmedukamel.arrafni.dto.category.sub.SubCategoryResponse;
import org.ahmedukamel.arrafni.mapper.category.sub.SubCategoryResponseMapper;
import org.ahmedukamel.arrafni.model.SubCategory;
import org.ahmedukamel.arrafni.repository.SubCategoryRepository;
import org.ahmedukamel.arrafni.saver.FileSaver;
import org.ahmedukamel.arrafni.saver.category.sub.SubCategorySaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.updater.category.sub.SubCategoryUpdater;
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
public class SubCategoryService implements ISubCategoryService {
    private final SubCategoryResponseMapper mapper;
    private final SubCategoryRepository repository;
    private final SubCategoryUpdater updater;
    private final SubCategorySaver saver;
    private final FileSaver fileSaver;

    @Override
    public Object createCategory(Object object, MultipartFile file) {
        SubCategoryDto request = (SubCategoryDto) object;
        SubCategory savedSubCategory = saver.apply(request, file);
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
    public Object uploadCategoryLogo(Integer id, MultipartFile logo) {
        SubCategory subCategory = DatabaseService.get(repository::findById, id, SubCategory.class);

        try {
            Files.deleteIfExists(PathConstants.SUB_CATEGORY_LOGO.resolve(subCategory.getLogo()));

            Optional<String> optionalLogoId = fileSaver.apply(logo, PathConstants.SUB_CATEGORY_LOGO);

            if (optionalLogoId.isEmpty()) {
                throw new IOException();
            }

            subCategory.setLogo(optionalLogoId.get());

            SubCategory updatedSubCategory = repository.save(subCategory);

            SubCategoryResponse response = mapper.apply(updatedSubCategory);
            return new ApiResponse(true, "Successful upload sub-category logo.", response);

        } catch (IOException exception) {
            throw new RuntimeException("Failed to upload sub-category logo", exception);
        }
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
    public Object getCategory(Integer id) {
        SubCategory subCategory = DatabaseService.get(repository::findById, id, SubCategory.class);
        SubCategoryResponse response = mapper.apply(subCategory);
        return new ApiResponse(true, "Successful Get Sub-Category.", response);
    }

    @Override
    public Object getCategories(int pageSize, int pageNumber) {
        final List<SubCategoryResponse> response = repository.selectPaginatedSubCategories(pageSize, pageSize * (pageNumber - 1)).stream().map(mapper).toList();
        return new ApiResponse(true, "Successful Get Sub-Categories.", response);
    }

    @Override
    public Object viewSubCategoryLogo(String logoId) {
        final byte[] image;

        try {
            Path path = PathConstants.SUB_CATEGORY_LOGO.resolve(logoId.strip());

            if (Files.exists(path)) {
                image = Files.readAllBytes(path);
            } else {
                image = new ClassPathResource("static/images/image-not-found.png")
                        .getContentAsByteArray();
            }

        } catch (IOException exception) {
            throw new RuntimeException("Failed to read sub category logo", exception);
        }

        return image;
    }
}