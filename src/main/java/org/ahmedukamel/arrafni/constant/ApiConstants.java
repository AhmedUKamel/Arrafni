package org.ahmedukamel.arrafni.constant;

public interface ApiConstants {
    String BASE_URL = "http://109.176.197.152:8088";
    String MAIN_CATEGORY_LOGO_API = BASE_URL + "/api/v1/main-category/public/logo?logo=%s";
    String SUB_CATEGORY_LOGO_API = BASE_URL + "/api/v1/sub-category/public/logo?logo=%s";
    String ANNOUNCEMENT_POSTER_API = BASE_URL + "/api/v1/announcement/public/%s/poster";
}