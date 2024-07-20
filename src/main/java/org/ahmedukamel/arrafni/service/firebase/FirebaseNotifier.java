package org.ahmedukamel.arrafni.service.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ahmedukamel.arrafni.constant.ApiConstants;
import org.ahmedukamel.arrafni.model.BusinessNotification;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
public class FirebaseNotifier
        implements BiConsumer<BusinessNotification, Collection<String>> {

    private final FirebaseMessaging firebaseMessaging;

    @SneakyThrows
    @Override
    public void accept(BusinessNotification businessNotification, Collection<String> tokens) {
        String image = Objects.nonNull(businessNotification.getImage())
                ? ApiConstants.BUSINESS_NOTIFICATION_LOGO_API.formatted(businessNotification.getImage())
                : null;

        Notification notification = Notification
                .builder()
                .setTitle(businessNotification.getTitle())
                .setBody(businessNotification.getBody())
                .setImage(image)
                .build();

        List<Message> messages = tokens.stream()
                .map(token -> Message
                        .builder()
                        .setNotification(notification)
                        .setToken(token)
                        .build()
                )
                .toList();

        firebaseMessaging.sendEach(messages);
    }
}
