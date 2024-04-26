package org.ahmedukamel.sohagy.dto.response;

public record ApiResponse(
        boolean success,
        String message,
        Object data
) {
}