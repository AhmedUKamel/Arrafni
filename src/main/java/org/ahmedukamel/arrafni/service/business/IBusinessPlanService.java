package org.ahmedukamel.arrafni.service.business;

public interface IBusinessPlanService {
    Object createBusinessPlan(Object object);

    void setActiveStatus(Integer id, boolean active);

    Object readBusinessPlan(Integer id);

    Object readBusinessPlans();

    Object readBusinessPlans(Integer pageSize, Integer pageNumber);
}