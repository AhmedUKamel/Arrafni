package org.ahmedukamel.sohagy.service.business;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.sohagy.constant.PathConstants;
import org.ahmedukamel.sohagy.dto.business.BusinessResponse;
import org.ahmedukamel.sohagy.dto.business.CreateBusinessRequest;
import org.ahmedukamel.sohagy.dto.response.ApiResponse;
import org.ahmedukamel.sohagy.mapper.business.BusinessResponseMapper;
import org.ahmedukamel.sohagy.model.Business;
import org.ahmedukamel.sohagy.repository.BusinessRepository;
import org.ahmedukamel.sohagy.saver.BusinessSaver;
import org.ahmedukamel.sohagy.saver.FileSaver;
import org.ahmedukamel.sohagy.service.db.DatabaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class BusinessManagementService implements IBusinessManagementService {
    final BusinessRepository businessRepository;
    final BusinessResponseMapper mapper;
    final BusinessSaver businessSaver;
    final FileSaver fileSaver;

    @Override
    public Object createBusiness(Object object, MultipartFile logoFile, MultipartFile[] picturesFiles) {
        CreateBusinessRequest request = (CreateBusinessRequest) object;
        Business business = businessSaver.apply(request);

        fileSaver.apply(logoFile, PathConstants.BUSINESS_LOGO).ifPresent(business::setLogo);
        Arrays.stream(picturesFiles).forEach(file -> fileSaver.apply(file, PathConstants.BUSINESS_PICTURES).ifPresent(business.getPictures()::add));
        Business savedBusiness = businessRepository.save(business);

        BusinessResponse response = mapper.apply(savedBusiness);
        return new ApiResponse(true, "Successful Create Business.", response);
    }

    @Override
    public Object readBusiness(Long id) {
        Business business = DatabaseService.get(businessRepository::findNonDeletedById, id, Business.class);
        BusinessResponse response = mapper.apply(business);
        return new ApiResponse(true, "Successful Read Business.", response);
    }

    @Override
    public Object updateBusiness(Long id, Object object) {
        return null;
    }

    @Override
    public void deleteBusiness(Long id) {
        Business business = DatabaseService.get(businessRepository::findNonDeletedById, id, Business.class);
        business.setDeleted(true);
        businessRepository.save(business);
    }

    @Override
    public void uploadLogo(Long id, MultipartFile file) {
        Business business = DatabaseService.get(businessRepository::findNonDeletedById, id, Business.class);
        try {
            Optional<String> result = fileSaver.apply(file, PathConstants.BUSINESS_LOGO);
            if (result.isPresent()) {
                Files.delete(PathConstants.BUSINESS_LOGO.resolve(business.getLogo()));

                business.setLogo(result.get());
                businessRepository.save(business);
            } else {
                throw new IOException("Error saving image.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void uploadImages(Long id, MultipartFile[] files) {
        Business business = DatabaseService.get(businessRepository::findNonDeletedById, id, Business.class);
        if (business.getPictures().size() + files.length > 5) {
            throw new RuntimeException("Maximum allowed pictures is 5.");
        }

        Arrays.stream(files).forEach(file -> fileSaver.apply(file, PathConstants.BUSINESS_PICTURES).ifPresent(business.getPictures()::add));
        businessRepository.save(business);
    }

    @Override
    public void deleteImages(Long id, List<String> imagesName) {
        Assert.noNullElements(imagesName, "Images names contains null value.");

        Business business = DatabaseService.get(businessRepository::findNonDeletedById, id, Business.class);
        imagesName.stream()
                .filter(business.getPictures()::contains)
                .forEach(business.getPictures()::remove);

        if (business.getPictures().isEmpty()) {
            throw new RuntimeException("Can not delete all images for business.");
        }

        imagesName.forEach(imageName -> {
            try {
                Files.delete(PathConstants.BUSINESS_PICTURES.resolve(imageName));
            } catch (IOException exception) {
                business.getPictures().add(imageName);
            }
        });

        businessRepository.save(business);
    }
}