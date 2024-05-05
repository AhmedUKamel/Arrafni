package org.ahmedukamel.arrafni.service.business;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.business.BusinessResponse;
import org.ahmedukamel.arrafni.dto.business.ShortBusinessResponse;
import org.ahmedukamel.arrafni.mapper.business.BusinessResponseMapper;
import org.ahmedukamel.arrafni.mapper.business.ShortBusinessResponseMapper;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicBusinessService implements IPublicBusinessService {
    final ShortBusinessResponseMapper shortMapper;
    final BusinessRepository repository;
    final BusinessResponseMapper mapper;

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
        BusinessResponse response = mapper.apply(new Object[]{business, 0});
        return new ApiResponse(true, "Successful Get Business.", response);
    }
}