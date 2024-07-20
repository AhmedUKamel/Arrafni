package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.BusinessNotificationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessNotificationPlanRepository
        extends JpaRepository<BusinessNotificationPlan, Integer>,
        JpaSpecificationExecutor<BusinessNotificationPlan> {

    Optional<BusinessNotificationPlan> findByIdAndActiveIsTrue(Integer businessNotificationId);

    boolean existsByName(String name);
}
