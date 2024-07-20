package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.BusinessNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessNotificationRepository
        extends JpaRepository<BusinessNotification, Long> {
}
