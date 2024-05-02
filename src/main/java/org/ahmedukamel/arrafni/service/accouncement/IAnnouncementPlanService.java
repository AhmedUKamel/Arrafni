package org.ahmedukamel.arrafni.service.accouncement;

public interface IAnnouncementPlanService {
    Object createAnnouncementPlan(Object object);

    void setActiveStatus(Integer id, boolean active);

    Object readAnnouncementPlan(Integer id);

    Object readAnnouncementPlans(long pageSize, long pageNumber);

    Object getAnnouncementPlan(Integer id);

    Object getAnnouncementPlans(long pageSize, long pageNumber);
}