package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.OfferPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferPlanRepository extends JpaRepository<OfferPlan, Integer> {
    Optional<OfferPlan> findByIdAndActiveIsTrue(Integer id);

    Page<OfferPlan> findAllByActiveIsTrue(Pageable pageable);

    boolean existsByName(String name);
}