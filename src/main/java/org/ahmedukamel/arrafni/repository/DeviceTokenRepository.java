package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.DeviceToken;
import org.ahmedukamel.arrafni.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
    Optional<DeviceToken> findByToken(String token);

    List<DeviceToken> findAllByRegion(Region region);
}
