package org.ahmedukamel.arrafni.service.business;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.constant.PathConstants;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.business.CreateBusinessRequest;
import org.ahmedukamel.arrafni.dto.business.OwnerBusinessResponse;
import org.ahmedukamel.arrafni.dto.business.UpdateBusinessRequest;
import org.ahmedukamel.arrafni.mapper.business.OwnerBusinessResponseMapper;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.BusinessLicence;
import org.ahmedukamel.arrafni.model.BusinessPlan;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.repository.BusinessLicenceRepository;
import org.ahmedukamel.arrafni.repository.BusinessPlanRepository;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.saver.BusinessSaver;
import org.ahmedukamel.arrafni.saver.FileSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.updater.business.UpdateBusinessRequestUpdater;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BusinessManagementService implements IBusinessManagementService {
    private final BusinessLicenceRepository businessLicenceRepository;
    private final BusinessPlanRepository businessPlanRepository;
    private final BusinessRepository businessRepository;
    private final UpdateBusinessRequestUpdater updater;
    private final OwnerBusinessResponseMapper mapper;
    private final BusinessRepository repository;
    private final BusinessSaver businessSaver;
    private final FileSaver fileSaver;

    @Override
    public Object createBusiness(Object object, MultipartFile logoFile, MultipartFile[] picturesFiles) {
        CreateBusinessRequest request = (CreateBusinessRequest) object;
        Business business = businessSaver.apply(request);

        fileSaver.apply(logoFile, PathConstants.BUSINESS_LOGO).ifPresent(business::setLogo);
        Arrays.stream(picturesFiles).forEach(file -> fileSaver.apply(file, PathConstants.BUSINESS_PICTURES).ifPresent(business.getPictures()::add));
        Business savedBusiness = repository.save(business);

        OwnerBusinessResponse response = mapper.apply(savedBusiness);
        return new ApiResponse(true, "Successful Create Business.", response);
    }

    @Override
    public Object readBusiness(Long id) {
        Business business = getBusiness(id);
        OwnerBusinessResponse response = mapper.apply(business);
        return new ApiResponse(true, "Successful Read Business.", response);
    }

    @Override
    public Object updateBusiness(Long id, Object object) {
        Business business = getBusiness(id);
        UpdateBusinessRequest request = (UpdateBusinessRequest) object;

        Business updatedBusiness = updater.apply(business, request);

        OwnerBusinessResponse response = mapper.apply(updatedBusiness);
        String message = "Successful Update Business.";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getMyBusinesses(int pageSize, int pageNumber) {
        User user = ContextHolderUtils.getUser();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Business> businesses = repository.findAllByOwner_Id(user.getId(), pageable);

        Page<OwnerBusinessResponse> response = businesses.map(mapper);
        String message = "Owner businesses retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object buyBusinessLicence(Long businessId, Integer planId) {
        Business business = DatabaseService.get(businessRepository::findById, businessId, Business.class);

        BusinessPlan plan = DatabaseService.get(businessPlanRepository::findById, planId, BusinessPlan.class);

        User user = ContextHolderUtils.getUser();

        if (!business.getOwner().getId().equals(user.getId())) {
            throw new IllegalStateException("User is not owner of this business");
        }

        if (business.isActive()) {
            throw new IllegalStateException("Business is already active");
        }

        BusinessLicence licence = new BusinessLicence();
        licence.setBusiness(business);
        licence.setPlan(plan);

        businessLicenceRepository.save(licence);

        String message = "Business licence requested successfully";

        return new ApiResponse(true, message, "");
    }

    @Override
    public void deleteBusiness(Long id) {
        Business business = getBusiness(id);
        business.setDeleted(true);
        repository.save(business);
        businessLicenceRepository.deleteAllByBusinessAndValidIsFalse(business);
    }

    @Override
    public void uploadLogo(Long id, MultipartFile file) {
        Business business = getBusiness(id);
        try {
            Optional<String> result = fileSaver.apply(file, PathConstants.BUSINESS_LOGO);
            if (result.isPresent()) {
                Files.delete(PathConstants.BUSINESS_LOGO.resolve(business.getLogo()));

                business.setLogo(result.get());
                repository.save(business);
            } else {
                throw new IOException("Error saving image.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void uploadImages(Long id, MultipartFile[] files) {
        Business business = getBusiness(id);
        if (business.getPictures().size() + files.length > 5) {
            throw new RuntimeException("Maximum allowed pictures is 5.");
        }

        Arrays.stream(files).forEach(file -> fileSaver.apply(file, PathConstants.BUSINESS_PICTURES).ifPresent(business.getPictures()::add));
        repository.save(business);
    }

    @Override
    public void deleteImages(Long id, List<String> imagesName) {
        Business business = getBusiness(id);
        imagesName.stream()
                .flatMap(Stream::ofNullable)
                .filter(business.getPictures()::contains)
                .forEach(business.getPictures()::remove);

        if (business.getPictures().isEmpty()) {
            throw new RuntimeException("Can not delete all images for business.");
        }

        if (imagesName.isEmpty()) {
            throw new RuntimeException("Can not find valid images for business.");
        }

        imagesName.forEach(imageName -> {
            try {
                if (Files.exists(PathConstants.BUSINESS_PICTURES.resolve(imageName))) {
                    Files.delete(PathConstants.BUSINESS_PICTURES.resolve(imageName));
                }
            } catch (IOException exception) {
                business.getPictures().add(imageName);
            }
        });

        repository.save(business);
    }

    public Business getBusiness(Long id) {
        Function<Long, Optional<Business>> function = (businessId) -> ContextHolderUtils.getUser()
                .getBusinesses()
                .stream()
                .filter(i -> i.getId().equals(businessId))
                .findFirst();
        return DatabaseService.get(function, id, Business.class);
    }
}