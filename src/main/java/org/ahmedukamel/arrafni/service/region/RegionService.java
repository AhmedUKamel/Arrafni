package org.ahmedukamel.arrafni.service.region;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.region.RegionDto;
import org.ahmedukamel.arrafni.mapper.region.RegionDtoMapper;
import org.ahmedukamel.arrafni.model.Region;
import org.ahmedukamel.arrafni.repository.RegionRepository;
import org.ahmedukamel.arrafni.saver.region.RegionSaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.updater.region.RegionUpdater;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService implements IRegionService {
    final RegionRepository repository;
    final RegionDtoMapper mapper;
    final RegionUpdater updater;
    final RegionSaver saver;

    @Override
    public Object createRegion(Object object) {
        RegionDto request = (RegionDto) object;
        Region savedRegion = saver.apply(request);
        RegionDto response = mapper.apply(savedRegion);
        return new ApiResponse(true, "Successful Create Region.", response);
    }

    @Override
    public Object updateRegion(Integer id, Object object) {
        RegionDto request = (RegionDto) object;
        Region region = DatabaseService.get(repository::findById, id, Region.class);
        Region updatedRegion = updater.apply(region, request);
        RegionDto response = mapper.apply(updatedRegion);
        return new ApiResponse(true, "Successful Update Region.", response);
    }

    @Override
    public void deleteRegion(Integer id) {
        Region region = DatabaseService.get(repository::findById, id, Region.class);
        repository.delete(region);
    }

    @Override
    public Object getRegion(Integer id) {
        Region region = DatabaseService.get(repository::findById, id, Region.class);
        RegionDto response = mapper.apply(region);
        return new ApiResponse(true, "Successful Get Region.", response);
    }

    @Override
    public Object getRegions() {
        List<RegionDto> response = repository
                .findAll()
                .stream()
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Get Regions.", response);
    }

    @Override
    public Object searchRegions(String word) {
        List<RegionDto> response = repository
                .searchRegions(word.strip())
                .stream()
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Search Regions.", response);
    }
}