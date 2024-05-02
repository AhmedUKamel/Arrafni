package org.ahmedukamel.arrafni.model.enumration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    ANNOUNCEMENT(1),
    BUSINESS(2),
    SECURITY(3),
    PAYMENT(4),
    GENERAL(5);

    private final int code;
}