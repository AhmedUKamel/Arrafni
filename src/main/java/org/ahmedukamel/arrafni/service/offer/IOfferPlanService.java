package org.ahmedukamel.arrafni.service.offer;

public interface IOfferPlanService {
    Object createOfferPlan(Object object);

    void setActiveStatus(Integer id, boolean active);

    Object readOfferPlan(Integer id);

    Object readOfferPlans(int pageSize, int pageNumber);

    Object getOfferPlan(Integer id);

    Object getOfferPlans(int pageSize, int pageNumber);
}