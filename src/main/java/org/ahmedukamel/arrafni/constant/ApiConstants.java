package org.ahmedukamel.arrafni.constant;

public interface ApiConstants {
    String BASE_URL = "https://api.arrafni.com";
    String MAIN_CATEGORY_LOGO_API = BASE_URL + "/api/v1/main-category/public/logo?logo=%s";
    String SUB_CATEGORY_LOGO_API = BASE_URL + "/api/v1/sub-category/public/logo?logo=%s";
    String ANNOUNCEMENT_POSTER_API = BASE_URL + "/api/v1/announcement/public/%s/poster";
    String OFFER_POSTER_API = BASE_URL + "/api/v1/offer/public/%s/poster";
    String BUSINESS_LOGO_API = BASE_URL + "/api/v1/business/public/logo?id=%s";
    String BUSINESS_NOTIFICATION_LOGO_API = BASE_URL + "/api/v1/business/notification/public/picture?id=%s";
}