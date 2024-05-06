package org.ahmedukamel.arrafni.service.other;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.function.Function;

@Component
public class OperatingSystemParser implements Function<String, String> {
    @Override
    public String apply(String userAgent) {
        String operatingSystem = "Unknown";

        if (StringUtils.hasLength(userAgent)) {
            userAgent = userAgent.toLowerCase();

            if (userAgent.contains("windows")) {
                operatingSystem = "Windows";
            } else if (userAgent.contains("mac")) {
                operatingSystem = "Macintosh";
            } else if (userAgent.contains("linux")) {
                operatingSystem = "Linux";
            } else if (userAgent.contains("android")) {
                operatingSystem = "Android";
            } else if (userAgent.contains("iphone") || userAgent.contains("ipad")) {
                operatingSystem = "iOS";
            }
        }

        return operatingSystem;
    }
}