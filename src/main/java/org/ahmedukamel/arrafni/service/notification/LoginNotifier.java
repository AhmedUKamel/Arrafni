package org.ahmedukamel.arrafni.service.notification;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.model.Notification;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.model.UserNotification;
import org.ahmedukamel.arrafni.model.enumration.NotificationType;
import org.ahmedukamel.arrafni.repository.UserNotificationRepository;
import org.ahmedukamel.arrafni.service.other.OperatingSystemParser;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
public class LoginNotifier implements BiConsumer<User, HttpServletRequest> {
    final UserNotificationRepository repository;
    final OperatingSystemParser parser;

    @Override
    public void accept(User user, HttpServletRequest request) {
        String os = parser.apply(request.getHeader(HttpHeaders.USER_AGENT));
        String ip = request.getRemoteAddr();
        String message = "New login to your account, IPv4 = %s, device = %s.".formatted(ip, os);

        System.out.println(message);
//        Notification notification = new Notification();
//        notification.setType(NotificationType.SECURITY);
//        notification.setTimestamp(LocalDateTime.now());
//        notification.setMessage(message);
//
//        UserNotification userNotification = new UserNotification();
//        userNotification.setUser(user);
//        userNotification.setNotification(notification);
//        repository.save(userNotification);
    }
}