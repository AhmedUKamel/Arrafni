package org.ahmedukamel.arrafni.service.business;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ahmedukamel.arrafni.constant.PathConstants;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.business.BusinessResponse;
import org.ahmedukamel.arrafni.dto.business.ShortBusinessResponse;
import org.ahmedukamel.arrafni.mapper.business.BusinessResponseMapper;
import org.ahmedukamel.arrafni.mapper.business.ShortBusinessResponseMapper;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.Region;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.model.WishlistItem;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.repository.RegionRepository;
import org.ahmedukamel.arrafni.repository.UserRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessService implements IBusinessService {
    final ShortBusinessResponseMapper shortMapper;
    final RegionRepository regionRepository;
    final BusinessResponseMapper mapper;
    final BusinessRepository repository;
    final UserRepository userRepository;
    private final BusinessRepository businessRepository;

    @Override

    public Object searchBusinessesByLocation(String word, Double latitude, Double longitude, long pageSize, long pageNumber) {
        List<ShortBusinessResponse> response = repository
                .searchNearestBusinessByLocationWithPagination(word.strip(), latitude, longitude, pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map(shortMapper)
                .toList();
        return new ApiResponse(true, "Successful Get Businesses.", response);
    }

    @Override
    public Object readBusinessesBySubCategoryByLocation(Integer id, Double latitude, Double longitude, long pageSize, long pageNumber) {
        List<ShortBusinessResponse> response = repository
                .selectNearestBusinessBySubCategoryAndLocationWithPagination(id, latitude, longitude, pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map(shortMapper)
                .toList();
        return new ApiResponse(true, "Successful Get Businesses.", response);
    }

    @Override
    public Object readBusinessesByMainCategoryByLocation(Integer id, Double latitude, Double longitude, long pageSize, long pageNumber) {
        List<ShortBusinessResponse> response = repository
                .selectNearestBusinessByMainCategoryAndLocationWithPagination(id, latitude, longitude, pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map(shortMapper)
                .toList();
        return new ApiResponse(true, "Successful Get Businesses.", response);
    }

    @Override
    public Object searchBusinessesByRegionId(String word, Integer regionId, long pageSize, long pageNumber) {
        Region region = DatabaseService.get(regionRepository::findById, regionId, Region.class);
        List<ShortBusinessResponse> response = repository
                .searchNearestBusinessByRegionWithPagination(word.strip(), region, pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map((b) -> shortMapper.apply(new Object[]{b, 0.0}))
                .toList();
        return new ApiResponse(true, "Successful Get Businesses.", response);
    }

    @Override
    public Object readBusinessesBySubCategoryByRegionId(Integer id, Integer regionId, long pageSize, long pageNumber) {
        Region region = DatabaseService.get(regionRepository::findById, regionId, Region.class);
        List<ShortBusinessResponse> response = repository
                .selectNearestBusinessBySubCategoryAndRegionWithPagination(id, region, pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map((b) -> shortMapper.apply(new Object[]{b, 0.0}))
                .toList();
        return new ApiResponse(true, "Successful Get Businesses.", response);
    }

    @Override
    public Object readBusinessesByMainCategoryByRegionId(Integer id, Integer regionId, long pageSize, long pageNumber) {
        Region region = DatabaseService.get(regionRepository::findById, regionId, Region.class);
        List<ShortBusinessResponse> response = repository
                .selectNearestBusinessByMainCategoryAndRegionWithPagination(id, region, pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map((b) -> shortMapper.apply(new Object[]{b, 0.0}))
                .toList();
        return new ApiResponse(true, "Successful Get Businesses.", response);
    }

    @Override
    public Object readBusiness(Long id) {
        Business business = DatabaseService.get(repository::findVisibleById, id, Business.class);
        repository.incrementVisits(id);
        BusinessResponse response = mapper.apply(business);
        return new ApiResponse(true, "Successful Get Business.", response);
    }

    @Override
    public Object readRecentAddedBusinessesByRegion(Integer regionId, int pageSize, int pageNumber) {
        Region region = DatabaseService.get(regionRepository::findById, regionId, Region.class);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("creation").descending());

        Page<Business> businesses = businessRepository.findAllByRegionAndLockedIsFalseAndActiveIsTrueAndDeletedIsFalseAndEnabledIsTrue(region, pageable);

        Page<BusinessResponse> response = businesses.map(mapper);
        String message = "Business retrieved successfully";

        return new ApiResponse(true, message, response);
    }

    @Override
    public void setFavoriteBusiness(Long id, Boolean isFavorite) {
        User user = ContextHolderUtils.getUser();
        Business business = DatabaseService.get(repository::findVisibleById, id, Business.class);
        Optional<WishlistItem> itemOptional = user.getWishlist()
                .getItems()
                .stream()
                .filter(wishlistItem -> wishlistItem.getBusiness().getId().equals(business.getId()))
                .findFirst();
        if (isFavorite && itemOptional.isEmpty()) {
            WishlistItem wishlistItem = new WishlistItem();
            wishlistItem.setBusiness(business);
            wishlistItem.setWishlist(user.getWishlist());
            user.getWishlist()
                    .getItems()
                    .add(wishlistItem);
        } else if (!isFavorite && itemOptional.isPresent()) {
            user.getWishlist()
                    .getItems()
                    .remove(itemOptional.get());
        }
        userRepository.save(user);
    }

    @SneakyThrows
    @Override
    public byte[] viewBusinessLogo(String logoId) {
        return getBytes(logoId, PathConstants.BUSINESS_LOGO);
    }

    @SneakyThrows
    @Override
    public byte[] viewBusinessPicture(String pictureId) {
        return getBytes(pictureId, PathConstants.BUSINESS_PICTURES);
    }

    @SneakyThrows
    @Override
    public byte[] viewBusinessNotificationPicture(String pictureId) {
        return getBytes(pictureId, PathConstants.BUSINESS_NOTIFICATION_PICTURE);
    }

    private static byte[] getBytes(String logoId, Path path) throws IOException {
        final byte[] image;

        Path imagePath = path.resolve(logoId);

        if (Files.exists(imagePath)) {
            image = Files.readAllBytes(imagePath);
        } else {
            image = new ClassPathResource("static/images/image-not-found.png")
                    .getContentAsByteArray();
        }

        return image;
    }
}