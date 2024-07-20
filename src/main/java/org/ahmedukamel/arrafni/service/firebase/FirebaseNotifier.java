package org.ahmedukamel.arrafni.service.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.model.BusinessNotification;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
public class FirebaseNotifier
        implements BiConsumer<BusinessNotification, Collection<String>> {

    private final FirebaseMessaging firebaseMessaging;

    @Override
    public void accept(BusinessNotification businessNotification, Collection<String> tokens) {
        Notification notification = Notification
                .builder()
                .setTitle(businessNotification.getTitle())
                .setBody(businessNotification.getBody())
                .setImage(businessNotification.getImage())
                .build();

        List<Message> messages = tokens.stream()
                .map(token -> Message
                        .builder()
                        .setNotification(notification)
                        .setToken(token)
                        .build()
                )
                .toList();

        try {
            firebaseMessaging.sendEach(messages);
        } catch (Exception exception) {
            throw new RuntimeException("Failed send messages", exception);
        }
    }
}
