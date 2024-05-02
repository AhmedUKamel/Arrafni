package org.ahmedukamel.arrafni.dto.api;

public record ApiResponse(
        boolean success,
        String message,
        Object data
) {
}