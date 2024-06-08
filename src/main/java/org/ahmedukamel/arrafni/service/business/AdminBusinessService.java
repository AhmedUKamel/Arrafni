package org.ahmedukamel.arrafni.service.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.business.AdminBusinessResponse;
import org.ahmedukamel.arrafni.dto.business.BusinessLicenceResponse;
import org.ahmedukamel.arrafni.dto.business.CriticalBusinessData;
import org.ahmedukamel.arrafni.mapper.business.AdminBusinessResponseMapper;
import org.ahmedukamel.arrafni.mapper.business.BusinessLicenceResponseMapper;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.BusinessLicence;
import org.ahmedukamel.arrafni.model.SubCategory;
import org.ahmedukamel.arrafni.model.embeddable.Location;
import org.ahmedukamel.arrafni.repository.BusinessLicenceRepository;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.repository.SubCategoryRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AdminBusinessService implements IAdminBusinessService {
    private final BusinessLicenceResponseMapper businessLicenceResponseMapper;
    private final AdminBusinessResponseMapper adminBusinessResponseMapper;
    private final BusinessLicenceRepository businessLicenceRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final BusinessRepository businessRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void setLockStatus(Long id, boolean locked) {
        Business business = DatabaseService.get(businessRepository::findNonDeletedById, id, Business.class);
        business.setLocked(locked);
        businessRepository.save(business);
    }

    @Override
    public void activateBusiness(Long id) {
        Business business = DatabaseService.get(businessRepository::findNonDeletedById, id, Business.class);
        business.setEnabled(true);
        businessRepository.save(business);
    }

    @Override
    public void activateBusinessLicence(Long id) {
        BusinessLicence licence = DatabaseService.get(businessLicenceRepository::findById, id, BusinessLicence.class);

        Business business = licence.getBusiness();

        if (business.isActive() ||
            (Objects.nonNull(business.getExpiration()) && business.getExpiration().isAfter(LocalDateTime.now()))) {

            throw new RuntimeException("Business already activated");
        }

        LocalDateTime expiration = LocalDateTime.now().plusMinutes(licence.getPlan().getDays());

        business.setActive(true);
        business.setExpiration(expiration);

        businessRepository.save(business);

        licence.setManual(true);
        licence.setValid(true);
        licence.setExpiration(expiration);

        businessLicenceRepository.save(licence);
    }

    @Override
    public void approveBusinessUpdate(Long id) {
        Business business = DatabaseService.get(businessRepository::findNonDeletedById, id, Business.class);

        if (!business.isUpdate() || !Strings.hasLength(business.getChange())) {
            business.setUpdate(false);
            businessRepository.save(business);

            throw new RuntimeException("Business does not contains change");
        }

        try {
            CriticalBusinessData criticalBusinessData = objectMapper
                    .readValue(business.getChange(), CriticalBusinessData.class);

            if (Strings.hasLength(criticalBusinessData.address())) {
                business.setAddress(criticalBusinessData.address().strip());
            }

            if (Strings.hasLength(criticalBusinessData.series())) {
                business.setAddress(criticalBusinessData.series().strip());
            }

            if (Objects.nonNull(criticalBusinessData.latitude()) && Objects.nonNull(criticalBusinessData.longitude())) {
                Location location = new Location(criticalBusinessData.latitude(), criticalBusinessData.longitude());
                business.setLocation(location);
            }

            if (Objects.nonNull(criticalBusinessData.categories()) && !criticalBusinessData.categories().isEmpty()) {
                Set<SubCategory> subCategories = criticalBusinessData.categories()
                        .stream()
                        .flatMap(Stream::ofNullable)
                        .map(subCategoryRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());

                business.setSubCategories(subCategories);
            }

            business.setUpdate(false);
            business.setChange(null);

            businessRepository.save(business);

        } catch (IOException exception) {
            throw new RuntimeException("Error while parsing change", exception);
        }
    }

    @Override
    public Object getPendingActivationBusinesses(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Business> businesses = businessRepository.findAllByEnabledIsFalse(pageable);

        Page<AdminBusinessResponse> response = businesses.map(adminBusinessResponseMapper);
        String message = "Businesses retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getPendingActivationBusinessLicences(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<BusinessLicence> licences = businessLicenceRepository.findAllByValidIsFalse(pageable);

        Page<BusinessLicenceResponse> response = licences.map(businessLicenceResponseMapper);
        String message = "Businesses retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getPendingUpdateApprovalBusinesses(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Business> businesses = businessRepository.findAllByUpdateIsTrue(pageable);

        Page<AdminBusinessResponse> response = businesses.map(adminBusinessResponseMapper);
        String message = "Businesses retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getAllBusinesses(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Business> businesses = businessRepository.findAll(pageable);

        Page<AdminBusinessResponse> response = businesses.map(adminBusinessResponseMapper);
        String message = "Businesses retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getBusinessesByLockStatus(boolean locked, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Business> businesses = businessRepository.findAllByLocked(locked, pageable);

        Page<AdminBusinessResponse> response = businesses.map(adminBusinessResponseMapper);
        String message = "Businesses retrieved successfully";

        return new ApiResponse(true, message, response);
    }
}