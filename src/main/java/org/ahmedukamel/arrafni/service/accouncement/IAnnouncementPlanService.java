package org.ahmedukamel.arrafni.service.accouncement;

public interface IAnnouncementPlanService {
    Object createAnnouncementPlan(Object object);

    void setActiveStatus(Integer id, boolean active);

    Object readAnnouncementPlan(Integer id);

    Object readAnnouncementPlans(int pageSize, int pageNumber);

    Object getAnnouncementPlan(Integer id);

    Object getAnnouncementPlans(int pageSize, int pageNumber);
}