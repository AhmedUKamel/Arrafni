package org.ahmedukamel.arrafni.service.business;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.business.BusinessResponse;
import org.ahmedukamel.arrafni.dto.business.ShortBusinessResponse;
import org.ahmedukamel.arrafni.mapper.business.BusinessResponseMapper;
import org.ahmedukamel.arrafni.mapper.business.ShortBusinessResponseMapper;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.model.WishlistItem;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.repository.UserRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessService implements IBusinessService {
    final ShortBusinessResponseMapper shortMapper;
    final BusinessResponseMapper mapper;
    final BusinessRepository repository;
    final UserRepository userRepository;

    @Override

    public Object searchBusinesses(String word, Double latitude, Double longitude, long pageSize, long pageNumber) {
        List<ShortBusinessResponse> response = repository
                .searchNearestBusinessWithPagination(word.strip(), latitude, longitude, pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map(shortMapper)
                .toList();
        return new ApiResponse(true, "Successful Get Businesses.", response);
    }

    @Override
    public Object readBusinessesBySubCategory(Integer id, Double latitude, Double longitude, long pageSize, long pageNumber) {
        List<ShortBusinessResponse> response = repository
                .selectNearestBusinessBySubCategoryWithPagination(id, latitude, longitude, pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map(shortMapper)
                .toList();
        return new ApiResponse(true, "Successful Get Businesses.", response);
    }

    @Override
    public Object readBusinessesByMainCategory(Integer id, Double latitude, Double longitude, long pageSize, long pageNumber) {
        List<ShortBusinessResponse> response = repository
                .selectNearestBusinessByMainCategoryWithPagination(id, latitude, longitude, pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map(shortMapper)
                .toList();
        return new ApiResponse(true, "Successful Get Businesses.", response);
    }

    @Override
    public Object readBusiness(Long id) {
        Business business = DatabaseService.get(repository::findVisibleById, id, Business.class);
        BusinessResponse response = mapper.apply(business);
        return new ApiResponse(true, "Successful Get Business.", response);
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
}