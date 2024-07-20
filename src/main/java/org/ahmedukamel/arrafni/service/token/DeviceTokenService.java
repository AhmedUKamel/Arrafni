package org.ahmedukamel.arrafni.service.token;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.token.DeviceTokenRequest;
import org.ahmedukamel.arrafni.model.DeviceToken;
import org.ahmedukamel.arrafni.model.Region;
import org.ahmedukamel.arrafni.repository.DeviceTokenRepository;
import org.ahmedukamel.arrafni.repository.RegionRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceTokenService implements IDeviceTokenService {
    private final DeviceTokenRepository deviceTokenRepository;
    private final RegionRepository regionRepository;

    @Override
    public Object updateDeviceToken(Object object) {
        DeviceTokenRequest request = (DeviceTokenRequest) object;
        Region region = DatabaseService.get(regionRepository::findById, request.regionId(), Region.class);
        String token = request.token().strip();

        DeviceToken deviceToken = deviceTokenRepository.findByToken(token).orElseGet(DeviceToken::new);

        deviceToken.setToken(token);
        deviceToken.setRegion(region);
        deviceTokenRepository.save(deviceToken);

        String message = "Device token updated successfully";

        return new ApiResponse(true, message, "");
    }

    @Override
    public Object deleteDeviceToken(String token) {
        DeviceToken deviceToken = DatabaseService.get(deviceTokenRepository::findByToken, token.strip(), DeviceToken.class);

        deviceTokenRepository.delete(deviceToken);

        String message = "Device token deleted successfully";

        return new ApiResponse(true, message, "");
    }
}
