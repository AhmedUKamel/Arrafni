package org.ahmedukamel.arrafni.constant;

import java.nio.file.Path;

public interface PathConstants {
    String prefix = "/home/ahmedukamel";
    Path BUSINESS_LOGO = Path.of(prefix + "/app/images/business/logo");
    Path BUSINESS_PICTURES = Path.of(prefix + "/app/images/business/pictures");
    Path USER_PROFILE_PICTURE = Path.of(prefix + "/app/images/profile/image");
}