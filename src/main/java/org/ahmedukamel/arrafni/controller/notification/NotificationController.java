package org.ahmedukamel.arrafni.controller.notification;

import jakarta.validation.constraints.Min;
import org.ahmedukamel.arrafni.model.enumration.NotificationType;
import org.ahmedukamel.arrafni.service.notification.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/notification")
public class NotificationController {
    final IUserNotificationService service;

    public NotificationController(UserNotificationService service) {
        this.service = service;
    }

    @PutMapping(value = "read/{notificationId}")
    public ResponseEntity<?> readNotification(@PathVariable(value = "notificationId") Long id) {
        service.readNotification(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable(value = "notificationId") Long id) {
        service.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "{notificationId}")
    public ResponseEntity<?> getNotification(@PathVariable(value = "notificationId") Long id) {
        return ResponseEntity.ok().body(service.getNotification(id));
    }

    @GetMapping
    public ResponseEntity<?> getNotifications(@Min(value = 1) @RequestParam(value = "size", defaultValue = "0") long pageSize,
                                              @Min(value = 1) @RequestParam(value = "number", defaultValue = "0") long pageNumber,
                                              @RequestParam(value = "type", required = false) NotificationType type) {
        return ResponseEntity.ok().body(service.getNotifications(pageSize, pageNumber, type));
    }
}